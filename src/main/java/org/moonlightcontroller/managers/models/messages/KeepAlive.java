package org.moonlightcontroller.managers.models.messages;

public class KeepAlive extends Message {
	private long dpid;

	// Default constructor to support Jersey
	public KeepAlive() {}
		
	public KeepAlive(int xid, long dpid) {
		super(xid);
		this.dpid = dpid;
	}
	
	public long getDpid() {
		return dpid;
	}
}	
