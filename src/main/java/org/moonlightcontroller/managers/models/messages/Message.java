package org.moonlightcontroller.managers.models.messages;

public abstract class Message implements IMessage {
	
	protected int xid;
	protected String type = getClass().getSimpleName();;
	protected String sourceAddr;

	public Message () {
		this.xid = (int)(Math.random() * Integer.MAX_VALUE);
	}
	
	public Message(int xid) {
		this.xid = xid;
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

	public String getSourceAddr() {
		return sourceAddr;
	}

	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}
}
