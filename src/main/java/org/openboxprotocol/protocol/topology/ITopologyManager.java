package org.openboxprotocol.protocol.topology;

import java.util.List;

public interface ITopologyManager {
	List<InstanceLocationSpecifier> getSubInstances(ILocationSpecifier loc);
	List<InstanceLocationSpecifier> getAllEndpoints();
}
