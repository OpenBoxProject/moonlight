package org.moonlightcontroller.managers.models.messages;

public class KeepAlive extends Message {
	private int dpid;

	// Default constructor to support Jersy
	public KeepAlive() {}
		
	public KeepAlive(int xid, int dpid) {
		super(xid);
		this.dpid = dpid;
	}
	
	public int getDpid() {
		return dpid;
	}
}	
