package org.moonlightcontroller.managers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.moonlightcontroller.managers.models.ConnectionInstance;
import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.ErrorMessage;
import org.moonlightcontroller.managers.models.messages.ErrorSubType;
import org.moonlightcontroller.managers.models.messages.HelloMessage;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.IResponseMessage;
import org.moonlightcontroller.managers.models.messages.KeepAliveMessage;
import org.moonlightcontroller.managers.models.messages.MessageResultType;
import org.moonlightcontroller.managers.models.messages.SuccessMessage;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class ConnectionManager implements ISouthboundClient{
	Map<InstanceLocationSpecifier, ConnectionInstance> instancesMapping;

	private static ConnectionManager instance;

	private ConnectionManager () {
		instancesMapping = new HashMap<>();
	}

	public synchronized static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}

		return instance;
	}

	public IResponseMessage updateInstanceKeepAlive(KeepAliveMessage message) {
		return updateInstanceKeepAlive(new InstanceLocationSpecifier(message.getDpid()+"", message.getDpid()));
	}

	public IResponseMessage updateInstanceKeepAlive(InstanceLocationSpecifier instanceLocationSpecifier) {
		ConnectionInstance data = instancesMapping.get(instanceLocationSpecifier);
		if (data != null) {
			data.updateKeepAlive();
			return new SuccessMessage();
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

	public IResponseMessage registerInstance(HelloMessage message) {
		try {
			InstanceLocationSpecifier key = new InstanceLocationSpecifier(message.getDpid() +"", message.getDpid());

			ConnectionInstance value = (new ConnectionInstance.Builder())
					.setDpid(message.getDpid())
					.setVersion(message.getVersion())
					.setCapabilities(message.getCapabilities())
					.build();
			instancesMapping.put(key, value);
			//TODO: section 3.5 in OpenBox spec
			//SetProcessingGraphRequest
			return new SuccessMessage();
		} catch (Exception e) {
			return new ErrorMessage(MessageResultType.BAD_REQUEST, ErrorSubType.ILLEGAL_ARGUMENT);
		}
	}

	@Override
	public void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender) {
		// TODO Auto-generated method stub
		
	}
}
