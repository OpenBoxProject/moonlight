package org.moonlightcontroller.topology;


/**
 * Interface which represents a location in the network
 * It can be an Instance or a Segment
 *
 */
public interface ILocationSpecifier {
	
	public interface Builder {
		public ILocationSpecifier build();
	}

	/**
	 * @return true if the location is an instance location
	 */
	boolean isSingleLocation();
	
	/**
	 * @return the id of the location
	 */
	long getId();
	
	/**
	 * support searching the topology tree
	 * @param m the id to find
	 * @return true if this.getId() == m
	 */
	boolean isMatch(long m);
	
	/**
	 * Finds the child of this ILocationSpecifier which has id equals to m
	 * @param m
	 * @return
	 */
	ILocationSpecifier findChild(long m);
}
