package org.moonlightcontroller.managers.models.messages;

public class GetParametersRequest implements IMessage {
	private String type;
	private int xid;
	
	public GetParametersRequest(int xid) {
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
