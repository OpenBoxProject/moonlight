package org.moonlightcontroller.topology;

import java.util.List;

/**
 * Interface for browsing the network topology
 *
 */
public interface ITopologyManager {
	/**
	 * Gets all sub-instances of a given location specifier
	 * @param loc
	 * @return
	 */
	List<InstanceLocationSpecifier> getSubInstances(ILocationSpecifier loc);
	
	/**
	 * Gets all Instance locations (leaves) of the current network topology
	 * @return
	 */
	List<InstanceLocationSpecifier> getAllEndpoints();
	
	/**
	 * Gets the ILocationSpecifer with the given id
	 * @param id 
	 * @return
	 */
	ILocationSpecifier resolve(long id);
	
	/**
	 * @return a list of all locations in bfs order
	 */
	List<ILocationSpecifier> bfs();
}
