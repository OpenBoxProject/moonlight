package org.openboxprotocol.protocol.topology;

import java.util.List;

public interface ITopologyManager {
	List<ILocationSpecifier> resolveLocation(ILocationSpecifier loc);
	List<ILocationSpecifier> getAllEndpoints();
}
