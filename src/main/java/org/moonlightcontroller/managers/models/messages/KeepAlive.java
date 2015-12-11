package org.moonlightcontroller.managers.models.messages;

public class KeepAlive implements IMessage {
	private String type;
	private int xid;
	private int dpid;

	public KeepAlive(int xid, int dpid) {
		this.type = "KeepAlive";
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
}