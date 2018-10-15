package org.moonlightcontroller.managers.models.messages;

public class KeepAlive extends Message {

	// Default constructor to support Jersey
	public KeepAlive() {}
		
	public KeepAlive(int xid, long dpid) {
		super(xid);
	}
}
