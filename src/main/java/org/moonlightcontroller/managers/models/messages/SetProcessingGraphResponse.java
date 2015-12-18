package org.moonlightcontroller.managers.models.messages;

public class SetProcessingGraphResponse implements IMessage {

	private int xid;
	private String type;
	public SetProcessingGraphResponse(String type, int xid) {
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
	public void setType(String type) {
		this.type = type;
	}
	

}