package org.moonlightcontroller.managers.models.messages;

public abstract class Message implements IMessage {
	
	protected int xid;
	protected String type;
	
	public Message () {}
	
	public Message(int xid) {
		this.xid = xid;
		type = getClass().getSimpleName();
	}
	
	public int getXid() {
		return xid;
	}
	
	public void setXid(int xid) {
		this.xid = xid;
	}
	
	public String getType() {
		return type;
	}

}
