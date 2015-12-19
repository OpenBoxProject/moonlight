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
	
	@Override
	public int getXid() {
		return xid;
	}
	
	@Override
	public void setXid(int xid) {
		this.xid = xid;
	}
	
	@Override
	public String getType() {
		return type;
	}
}