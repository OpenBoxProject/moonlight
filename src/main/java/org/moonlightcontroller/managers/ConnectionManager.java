package org.moonlightcontroller.managers;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.blocks.CustomBlock;
import org.moonlightcontroller.blocks.ObiType;
import org.moonlightcontroller.managers.models.ConnectionInstance;
import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.NullRequestSender;
import org.moonlightcontroller.managers.models.messages.*;
import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.processing.*;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;
import org.moonlightcontroller.topology.TopologyManager;
import org.openbox.dashboard.NetworkInformationService;
import org.openbox.dashboard.SouthboundProfiler;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Connection Manager is the class which holds connections to all active OBIs
 * It can send them messages and receives messages from them.
 */
public class ConnectionManager implements ISouthboundClient {

	private final static Logger LOG = Logger.getLogger(ConnectionManager.class.getName());

	Map<InstanceLocationSpecifier, ConnectionInstance> instancesMapping;
	Map<Integer, IMessage> messagesMapping;
	Map<Integer, IRequestSender> requestSendersMapping;

	private static ConnectionManager instance;

	private ConnectionManager () {
		instancesMapping = new ConcurrentHashMap<>();
		messagesMapping = new ConcurrentHashMap<>();
		requestSendersMapping = new ConcurrentHashMap<>();
	}

	public synchronized static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}

		return instance;
	}

	public Response handleKeepaliveRequest(KeepAlive message) {
		messagesMapping.put(message.getXid(), message);
		return handleKeepaliveRequest(getInstanceLocationSpecifier(message.getDpid()), message.getXid());
	}

	private InstanceLocationSpecifier getInstanceLocationSpecifier(long dpid) {
			return new InstanceLocationSpecifier(dpid);
	}

	private Response handleKeepaliveRequest(InstanceLocationSpecifier instanceLocationSpecifier, int xid) {
		ConnectionInstance connectionInstance = instancesMapping.get(instanceLocationSpecifier);
		if (connectionInstance != null) {
			connectionInstance.updateKeepAlive();
			return okResponse();
		}

		return internalErrorResponse();
	}

    public List<InstanceLocationSpecifier> getAliveInstances() {
        return getAliveInstances(TopologyManager.getInstance().getSegment());
    }

	public List<InstanceLocationSpecifier> getAliveInstances(ILocationSpecifier loc) {
		return TopologyManager.getInstance().getSubInstances(loc).stream()
				.filter(item -> isAlive(item)).collect(Collectors.toList());
	}

    public List<InstanceLocationSpecifier> getDeadInstances() {

        List<InstanceLocationSpecifier> deadInstances = instancesMapping.keySet().stream()
                .filter(item -> !isAlive(item))
                .collect(Collectors.toList());

        deadInstances.forEach(instancesMapping::remove);

        return deadInstances;
    }

    private boolean isAlive(InstanceLocationSpecifier item) {
		ConnectionInstance connectionInstance = instancesMapping.get(item);
		if (connectionInstance == null)
		    return false;
		else if (connectionInstance.getKeepAliveDate() == null)
		    return true;

		return connectionInstance.getKeepAliveDate()
				.isAfter(LocalDateTime.now().minusSeconds(connectionInstance.getKeepAliveInterval()*3));
	}

	public Response handleHelloRequest(String remoteAddress, Hello message) {
		int xid = message.getXid();
		messagesMapping.put(xid, message);
		try {
			long dpid = message.getDpid();
			InstanceLocationSpecifier key = getInstanceLocationSpecifier(dpid);

			ConnectionInstance value = (new ConnectionInstance.Builder())
					.setIp(message.getSourceAddr() != null ? message.getSourceAddr() : remoteAddress)
					.setDpid(dpid)
					.setVersion(message.getVersion())
					.setCapabilities(message.getCapabilities())
					.build();
			instancesMapping.put(key, value);

			IProcessingGraph processingGraph = ApplicationAggregator.getInstance().getProcessingGraph(key);
			List<JsonBlock> blocks = new ArrayList<>();
			List<JsonConnector> connectors = new ArrayList<>();
			if (processingGraph != null){
				List<IProcessingBlock> custom = processingGraph.getBlocks().stream()
						.filter(b -> b instanceof CustomBlock)
						.collect(Collectors.toList());
				if (custom.size() > 0){
					this.sendCustomBlocks(custom, key, message.getObiType());
				}
				blocks = translateBlocks(processingGraph.getBlocks());
				connectors = translateConnectors(processingGraph.getConnectors());
			}

			SetProcessingGraphRequest processMessage = new SetProcessingGraphRequest(0, dpid, null, blocks, connectors);

			SouthboundProfiler.getInstance().addOBI(message, processMessage);

			sendMessage(key, processMessage, new NullRequestSender());

			return okResponse();

		} catch (Exception e) {
			LOG.warning("Error occured while handling Hello message" + e.toString());
			e.printStackTrace();
			return internalErrorResponse();
		}
	}

	private void sendCustomBlocks(
			List<IProcessingBlock> custom, 
			InstanceLocationSpecifier loc, 
			ObiType obitype) throws InstanceNotAvailableException, IOException {
		
		if (obitype == null){
			obitype = ObiType.ClickObi;
		}
		for (IProcessingBlock block : custom) {
			CustomBlock customblock = (CustomBlock)block;
			AddCustomModuleRequest req = new AddCustomModuleRequest(
					0,
					customblock.getModuleName(), 
					customblock.getModuleContent(), 
					customblock.getTranslationObject(obitype));
			this.sendMessage(loc, req, new NullRequestSender());
		}
	}

	private List<JsonConnector> translateConnectors(List<IConnector> connectors) {
		return connectors.stream().map(connector -> translateConnector(connector)).collect(Collectors.toList());
	}

	private JsonConnector translateConnector(IConnector connector) {
		return new JsonConnector(connector.getSourceBlockId(), 
				connector.getSourceOutputPort(), 
				connector.getDestinationBlockId(),
				0);
	}


	private List<JsonBlock> translateBlocks(List<IProcessingBlock> blocks) {
		return blocks.stream().map(block -> translateBlock(block)).collect(Collectors.toList());
	}

	private JsonBlock translateBlock(IProcessingBlock block) {
		return new JsonBlock(block.getBlockType(), block.getId(), block.getConfiguration());
	}

	public Response handleSetProcessingGraphResponse(SetProcessingGraphResponse message) {
		IMessage originMessage = messagesMapping.get(message.getXid());

		if (originMessage == null) {
			return internalErrorResponse();
		}

		if (originMessage instanceof SetProcessingGraphRequest) {
			long dpid = ((SetProcessingGraphRequest)originMessage).getDpid();
			InstanceLocationSpecifier loc = getInstanceLocationSpecifier(dpid);
			ConnectionInstance instance = instancesMapping.get(loc);
			instance.setProcessingGraphConfiged(true);

			SouthboundProfiler.getInstance().onSetProcessingResponse(dpid); // update profiler

			return okResponse();
		}

		return badRequestResponse();
	}

	private Response badRequestResponse() {
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	private Response internalErrorResponse() {
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	private Response okResponse() {
		return Response.status(Status.OK).build();
	}

	@Override
	public void sendMessage(ILocationSpecifier loc, IMessage message, IRequestSender requestSender) throws InstanceNotAvailableException {
		int xid = XidGenerator.generateXid();
		sendMessage(xid, loc, message, requestSender);
	}

	public void sendMessage(int xid, ILocationSpecifier loc, IMessage message, IRequestSender requestSender) throws InstanceNotAvailableException {
		ConnectionInstance connectionInstance = instancesMapping.get(loc);
		if (connectionInstance == null) {
			throw new InstanceNotAvailableException();
		}

		message.setXid(xid);
		message.setDpid(connectionInstance.getDpid());
		messagesMapping.put(xid, message);
		connectionInstance.sendRequest(message, requestSender);
		requestSendersMapping.put(xid, requestSender);

	}

	public Response handleResponse(IMessage message) {
        profile(message);

        IRequestSender iRequestSender = requestSendersMapping.get(message.getXid());
        iRequestSender.onSuccess(message);
		return okResponse();
	}

	public Response handleErrorMessage(Error message) {
        profile(message);

        IRequestSender iRequestSender = requestSendersMapping.get(message.getXid());
		if (iRequestSender != null){
			iRequestSender.onFailure(message);			
		}
		return okResponse();
	}

	public Response handleListCapabilitiesResponse(ListCapabilitiesResponse message) {
		// see read response + update ListCapabilities in connectionInstansce
		// TODO: Dana this code is not thread safe - commenting out
		/*
		try {
			int xid = message.getXid();
			ConnectionInstance connectionInstance = instancesMapping.get(xid);
			connectionInstance.setCapabilities(message.getCapabilities());
		} catch (NullPointerException e) {
			return badRequestResponse();
		}
		*/
		
		return handleResponse(message);
	}



	public Response handleAlert(Alert message) {
        profile(message);

        ApplicationAggregator.getInstance().handleAlert(message);
		return okResponse();
	}

	public Response handleAddCustomModuleResponse(AddCustomModuleResponse message) {
        profile(message);

        IRequestSender iRequestSender = requestSendersMapping.get(message.getXid());
		iRequestSender.onSuccess(message);
		return okResponse();
	}

	private void profile(IMessage incomingMessage) {
        IMessage outMessage = messagesMapping.get(incomingMessage.getXid());
        if (outMessage != null)
            incomingMessage.setDpid(outMessage.getDpid());

        SouthboundProfiler.getInstance().onMessage(incomingMessage, true);
    }
}
