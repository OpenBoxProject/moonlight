package org.moonlightcontroller.managers;

/**
 * Interface for generating XIDs
 * It should be done in synchronized manner to avoid collisions
 */
public interface IXidGenerator {
	/**
	 * Generates XID in a thread safe manner
	 * @return the xid
	 */
	public int generateXid();
}
