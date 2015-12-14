package org.moonlightcontroller.managers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.managers.models.ConnectionInstance;
import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.KeepAlive;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphRequest;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphResponse;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class ConnectionManager implements IConnectionManager, ISouthboundClient{
	
	private final static Logger LOG = Logger.getLogger(ConnectionManager.class.getName());
	
	Map<InstanceLocationSpecifier, ConnectionInstance> instancesMapping;
	Map<Integer, IMessage> messagesMapping;
	Map<Integer, IRequestSender> requestSendersMapping;

	private static ConnectionManager instance;

	private ConnectionManager () {
		instancesMapping = new HashMap<>();
		messagesMapping = new HashMap<>();
		requestSendersMapping = new HashMap<>();
	}

	public synchronized static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}

		return instance;
	}

	@Override
	public Response handleKeepaliveRequest(KeepAlive message) {
		messagesMapping.put(message.getXid(), message);
		return handleKeepaliveRequest(new InstanceLocationSpecifier(message.getDpid()+"", message.getDpid()), message.getXid());
	}

	private Response handleKeepaliveRequest(InstanceLocationSpecifier instanceLocationSpecifier, int xid) {
		ConnectionInstance data = instancesMapping.get(instanceLocationSpecifier);
		if (data != null) {
			data.updateKeepAlive();
			return okResponse();
		}

		return internalErrorResponse();
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
	public Response handleHelloRequest(Hello message) {
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
			
			IProcessingGraph processingGraph = ApplicationAggregator.getInstance().getProcessingGraph(key);
			SetProcessingGraphRequest processMessage = new SetProcessingGraphRequest(xid, dpid, processingGraph);
			IRequestSender requestSender = requestSendersMapping.get(xid);
			value.sendRequest(processMessage, requestSender);
			return okResponse();

		} catch (Exception e) {
			LOG.warning("Error occured while handling Hello message" + e.toString());
			e.printStackTrace();
			return internalErrorResponse();
		}
	}

	private Response internalErrorResponse() {
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	public Response handleProcessingGraphResponse(SetProcessingGraphResponse message) {
		IMessage originMessage = messagesMapping.get(message.getXid());

		if (originMessage == null) {
			return internalErrorResponse();
		}
		
		if (originMessage instanceof SetProcessingGraphRequest) {
			int dpid = ((SetProcessingGraphRequest)originMessage).getDpid();
			InstanceLocationSpecifier loc = new InstanceLocationSpecifier(dpid +"", dpid);
			ConnectionInstance instance = instancesMapping.get(loc);
			instance.setProcessingGraphConfiged(true);
			return okResponse();
		}
		
		return Response.status(Status.BAD_REQUEST).build();
	}

	private Response okResponse() {
		return Response.status(Status.OK).build();
	}

	@Override
	public void sendMessage(ILocationSpecifier loc, IMessage message, IRequestSender requestSender) {
		ConnectionInstance connectionInstance = instancesMapping.get(loc);
		int xid = connectionInstance.sendRequest(message, requestSender);
		messagesMapping.put(xid, message);
		requestSendersMapping.put(xid, requestSender);
		
	}
}
