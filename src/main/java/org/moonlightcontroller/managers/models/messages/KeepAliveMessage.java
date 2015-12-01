package org.moonlightcontroller.managers.models.messages;

public class KeepAliveMessage implements IMessage {
	private String type;
	private int xid;
	private int dpid;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getXid() {
		return xid;
	}
	public void setXid(int xid) {
		this.xid = xid;
	}
	public int getDpid() {
		return dpid;
	}
	public void setDpid(int dpid) {
		this.dpid = dpid;
	}
}