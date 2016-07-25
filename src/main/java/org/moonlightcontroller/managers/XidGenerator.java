package org.moonlightcontroller.managers;

/**
 * Implementaion for XID generator
 *
 */
public class XidGenerator {
	private static int xid = 10000;
	private static XidGenerator instance;
	
	private XidGenerator() {
		
	}
	public synchronized static XidGenerator getInstance() {
		if (instance == null) {
			instance = new XidGenerator();
		}
		
		return instance;
	}

	public synchronized static int generateXid() {
		return ++xid;
	}
}
