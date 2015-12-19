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
import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.KeepAlive;
import org.moonlightcontroller.managers.models.messages.ReadResponse;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphRequest;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphResponse;
import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.IProcessingBlock;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.processing.JsonBlock;
import org.moonlightcontroller.processing.JsonConnector;
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
		return handleKeepaliveRequest(getInstanceLocationSpecifier(message.getDpid()), message.getXid());
	}

	private InstanceLocationSpecifier getInstanceLocationSpecifier(int ip) {
		List<InstanceLocationSpecifier> locs = TopologyManager.getInstance().getAllEndpoints().stream().filter(loc -> loc.getIp()== ip).collect(Collectors.toList());
		if (locs.isEmpty()) {
			LOG.warning("InstanceLocationSpecifier wasn't found for ip=" + ip);
			return new InstanceLocationSpecifier(ip+"", ip);
		}
		
		if (locs.size() > 1) {
			LOG.warning("Found more than a single InstanceLocationSpecifier for ip=" + ip);
		}
		
		return locs.get(0);
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
			InstanceLocationSpecifier key = getInstanceLocationSpecifier(dpid);

			ConnectionInstance value = (new ConnectionInstance.Builder())
					.setDpid(dpid)
					.setVersion(message.getVersion())
					.setCapabilities(message.getCapabilities())
					.build();
			instancesMapping.put(key, value);

			IProcessingGraph processingGraph = ApplicationAggregator.getInstance().getProcessingGraph(key);
			List<JsonBlock> blocks = translateBlocks(processingGraph.getBlocks());
			List<JsonConnector> connectors = translateConnectors(processingGraph.getConnectors());

			SetProcessingGraphRequest processMessage = new SetProcessingGraphRequest(xid, dpid, null, blocks, connectors);
			IRequestSender requestSender = requestSendersMapping.get(xid);
			value.sendRequest(processMessage, requestSender);
			return okResponse();

		} catch (Exception e) {
			LOG.warning("Error occured while handling Hello message" + e.toString());
			e.printStackTrace();
			return internalErrorResponse();
		}
	}

	private List<JsonConnector> translateConnectors(List<IConnector> connectors) {
		return connectors.stream().map(connector -> translateConnector(connector)).collect(Collectors.toList());
	}

	private JsonConnector translateConnector(IConnector connector) {
		return new JsonConnector(connector.getSourceBlockId(), 
				connector.getSourceOutputPort(), 
				connector.getDestinatinBlockId(),
				0);
	}


	private List<JsonBlock> translateBlocks(List<IProcessingBlock> blocks) {
		return blocks.stream().map(block -> translateBlock(block)).collect(Collectors.toList());
	}

	private JsonBlock translateBlock(IProcessingBlock block) {
		return new JsonBlock(block.getBlockType(), block.getId(), block.getConfiguration());
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
			InstanceLocationSpecifier loc = getInstanceLocationSpecifier(dpid);
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
		message.setXid(xid);
		messagesMapping.put(xid, message);
		requestSendersMapping.put(xid, requestSender);

	}

	public Response handleReadResponse(ReadResponse message) {
		IRequestSender iRequestSender = requestSendersMapping.get(message.getXid());
		iRequestSender.onSuccess(message);
		return okResponse();
	}
	
	public Response handleErrorMessage(Error message) {
		IRequestSender iRequestSender = requestSendersMapping.get(message.getXid());
		iRequestSender.onFailure(message);
		return okResponse();
	}
}
