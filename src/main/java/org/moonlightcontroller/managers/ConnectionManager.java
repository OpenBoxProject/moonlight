package org.moonlightcontroller.managers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.moonlightcontroller.managers.models.ConnectionInstance;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class ConnectionManager {
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
	
	public boolean updateInstanceKeepAlive(int xid, long dpid) {
		return updateInstanceKeepAlive(new InstanceLocationSpecifier(xid, dpid));
	}

	public boolean updateInstanceKeepAlive(InstanceLocationSpecifier instanceLocationSpecifier) {
		ConnectionInstance data = instancesMapping.get(instanceLocationSpecifier);
		if (data != null) {
			data.updateKeepAlive();
			return true;
		}
		
		return false;
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
	
	public boolean registerInstance(int xid, int dpid, String version, Map<String, List<String>> capabilities) {
		InstanceLocationSpecifier key = new InstanceLocationSpecifier(xid, dpid);
		ConnectionInstance value = (new ConnectionInstance.Builder())
				.setDpid(dpid)
				.setVersion(version)
				.setCapabilities(capabilities)
				.build();
		instancesMapping.put(key, value);
		//TODO: section 3.5 in OpenBox spec
		//SetProcessingGraphRequest
		
		return instancesMapping.get(key) != null;
	}
}
