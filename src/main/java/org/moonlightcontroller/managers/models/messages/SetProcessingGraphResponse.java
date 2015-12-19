package org.moonlightcontroller.managers.models.messages;

public class SetProcessingGraphResponse implements IMessage {

	private int xid;
	private String type;

	// Default constructor to support Jersy
	public SetProcessingGraphResponse() {}
		
	public SetProcessingGraphResponse(int xid) {
		this.xid = xid;
		this.type = this.getClass().getName();
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
	public void setType(String type) {
		this.type = type;
	}
}