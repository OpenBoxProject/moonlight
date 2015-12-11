package org.moonlightcontroller.managers.models.messages;

public class SetProcessingGraphResponse implements IMessage {

	private int xid;
	
	public SetProcessingGraphResponse(int xid) {
		this.xid = xid;
	}
	
	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public String getType() {
		return "SetProcessingGraphResponse";
	}

}
