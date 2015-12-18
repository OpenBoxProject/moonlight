package org.moonlightcontroller.managers.models.messages;

public class KeepAlive implements IMessage {
	private String type;
	private int xid;
	private int dpid;

	public KeepAlive(String type, int xid, int dpid) {
		this.type = type;
		this.xid = xid;
		this.dpid = dpid;
	}
	
	public String getType() {
		return type;
	}
	
	public int getXid() {
		return xid;
	}
	
	public int getDpid() {
		return dpid;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setXid(int xid) {
		this.xid = xid;
	}
	
	public void setDpid(int dpid) {
		this.dpid = dpid;
	}
}