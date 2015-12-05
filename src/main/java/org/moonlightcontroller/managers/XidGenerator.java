package org.moonlightcontroller.managers;

public class XidGenerator {
	private static int xid = 10000;
	private static XidGenerator instance;
	
	private XidGenerator() {
		
	}
	
	public static XidGenerator getInstance() {
		if (instance == null) {
			instance = new XidGenerator();
		}
		
		return instance;
	}

	public static int generateXid() {
		return ++xid;
	}
}
