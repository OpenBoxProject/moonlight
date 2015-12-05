package org.moonlightcontroller.managers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.managers.models.ConnectionInstance;
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

	private static ServerConnectionManager instance;

	private ServerConnectionManager () {
		instancesMapping = new HashMap<>();
		messagesMapping = new HashMap<>();
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

		return new ErrorMessage(MessageResultType.BAD_REQUEST, ErrorSubType.INTERNAL_ERROR);
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
		messagesMapping.put(message.getXid(), message);
		try {
			InstanceLocationSpecifier key = new InstanceLocationSpecifier(message.getDpid() +"", message.getDpid());

			ConnectionInstance value = (new ConnectionInstance.Builder())
					.setDpid(message.getDpid())
					.setVersion(message.getVersion())
					.setCapabilities(message.getCapabilities())
					.build();
			instancesMapping.put(key, value);
			//section 3.5 in OpenBox spec
			List<IStatement> statements = ApplicationAggregator.getInstance().getStatements(key);
			ProcessingGraphMessage processMessage = new ProcessingGraphMessage();
			value.sendRequest(processMessage, requestSender);
			
			return new SuccessMessage(message.getXid());
		} catch (Exception e) {
			return new ErrorMessage(MessageResultType.BAD_REQUEST, ErrorSubType.ILLEGAL_ARGUMENT);
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

		return new ErrorMessage(MessageResultType.BAD_REQUEST, ErrorSubType.ILLEGAL_ARGUMENT);
	}
}
