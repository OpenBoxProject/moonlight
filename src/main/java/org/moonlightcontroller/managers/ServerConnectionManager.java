package org.moonlightcontroller.managers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.managers.models.ConnectionInstance;
import org.moonlightcontroller.managers.models.HelloRequestSender;
import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.AcknowledgeMessage;
import org.moonlightcontroller.managers.models.messages.ErrorMessage;
import org.moonlightcontroller.managers.models.messages.ErrorSubType;
import org.moonlightcontroller.managers.models.messages.HelloMessage;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.IResponseMessage;
import org.moonlightcontroller.managers.models.messages.KeepAliveMessage;
import org.moonlightcontroller.managers.models.messages.MessageResultType;
import org.moonlightcontroller.managers.models.messages.ProcessingGraphMessage;
import org.moonlightcontroller.managers.models.messages.SuccessMessage;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class ServerConnectionManager implements IServerConnectionManager{
	Map<InstanceLocationSpecifier, ConnectionInstance> instancesMapping;
	Map<Integer, IMessage> messagesMapping;
	Map<Integer, IRequestSender> requestSendersMapping;

	private static ServerConnectionManager instance;

	private ServerConnectionManager () {
		instancesMapping = new HashMap<>();
		messagesMapping = new HashMap<>();
		requestSendersMapping = new HashMap<>();
	}

	public synchronized static ServerConnectionManager getInstance() {
		if (instance == null) {
			instance = new ServerConnectionManager();
		}

		return instance;
	}

	@Override
	public IResponseMessage handleKeepaliveRequest(KeepAliveMessage message) {
		messagesMapping.put(message.getXid(), message);
		return handleKeepaliveRequest(new InstanceLocationSpecifier(message.getDpid()+"", message.getDpid()), message.getXid());
	}

	private IResponseMessage handleKeepaliveRequest(InstanceLocationSpecifier instanceLocationSpecifier, int xid) {
		ConnectionInstance data = instancesMapping.get(instanceLocationSpecifier);
		if (data != null) {
			data.updateKeepAlive();
			return new SuccessMessage(xid);
		}

		return new ErrorMessage(xid, MessageResultType.BAD_REQUEST, ErrorSubType.INTERNAL_ERROR);
	}

	public List<InstanceLocationSpecifier> getAliveInstances(ILocationSpecifier loc) {
		return TopologyManager.getInstance().getSubInstances(loc).stream()
				.filter(item -> isAlive(item)).collect(Collectors.toList());
	}

	public List<InstanceLocationSpecifier> getAliveInstances() {
		return getAliveInstances(TopologyManager.getInstance().getSegment());
	}

	private boolean isAlive(InstanceLocationSpecifier item) {
		ConnectionInstance data = instancesMapping.get(item);
		return data.getKeepAliveDate()
				.isAfter(LocalDateTime.now().minusSeconds(data.getKeepAliveInterval()));
	}

	@Override
	public IResponseMessage handleHelloRequest(HelloMessage message) {
		int xid = message.getXid();
		messagesMapping.put(xid, message);
		try {
			int dpid = message.getDpid();
			InstanceLocationSpecifier key = new InstanceLocationSpecifier(dpid +"", dpid);

			ConnectionInstance value = (new ConnectionInstance.Builder())
					.setDpid(dpid)
					.setVersion(message.getVersion())
					.setCapabilities(message.getCapabilities())
					.build();
			instancesMapping.put(key, value);
			//section 3.5 in OpenBox spec 
			// TODO: need to generate a new xid or use the current?
			List<IStatement> statements = ApplicationAggregator.getInstance().getStatements(key);
			ProcessingGraphMessage processMessage = new ProcessingGraphMessage(xid, dpid, statements);
			HelloRequestSender requestSender = new HelloRequestSender(xid);
			value.sendRequest(processMessage, requestSender);
			requestSendersMapping.put(xid, requestSender);
			
			return new SuccessMessage(xid);
		} catch (Exception e) {
			return new ErrorMessage(xid, MessageResultType.BAD_REQUEST, ErrorSubType.ILLEGAL_ARGUMENT);
		}
	}

	public IResponseMessage handleProcessingGraphResponse(AcknowledgeMessage message) {
		IMessage originMessage = messagesMapping.get(message.getXid());

		if (originMessage instanceof ProcessingGraphMessage) {
			int dpid = ((ProcessingGraphMessage)originMessage).getDpid();
			InstanceLocationSpecifier loc = new InstanceLocationSpecifier(dpid +"", dpid);
			ConnectionInstance instance = instancesMapping.get(loc);
			instance.setProcessingGraphConfiged(true);
			return new SuccessMessage(message.getXid());
		}

		return new ErrorMessage(message.getXid(), MessageResultType.BAD_REQUEST, ErrorSubType.ILLEGAL_ARGUMENT);
	}
}
