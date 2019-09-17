package org.moonlightcontroller.managers.models.messages;

public abstract class Message implements IMessage {
	
	protected int xid;
	protected String type = getClass().getSimpleName();;
	protected String sourceAddr;
    private long dpid;

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

	public void setDpid(long dpid) {
		this.dpid = dpid;
	}

    public long getDpid() {
        return dpid;
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
