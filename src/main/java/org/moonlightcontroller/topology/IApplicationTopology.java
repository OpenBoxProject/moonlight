package org.moonlightcontroller.topology;

/**
 * Topology is responsible for resolving OBI ids to their actuals Instance implementations
 * It is a subset of of ITopologyManager interface passed to applications so they could query OBIs.
 */
public interface IApplicationTopology {
	/**
	 * Gets the Location Specifier class according to the given ids
	 * @param id
	 * @return
	 */
	ILocationSpecifier reoslve(long id);
}
