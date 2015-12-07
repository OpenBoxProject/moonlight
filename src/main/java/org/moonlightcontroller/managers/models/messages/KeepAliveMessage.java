package org.moonlightcontroller.managers.models.messages;

public class KeepAliveMessage implements IMessage {
	private String type;
	private int xid;
	private int dpid;

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