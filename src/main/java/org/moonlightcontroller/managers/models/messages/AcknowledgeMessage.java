package org.moonlightcontroller.managers.models.messages;

public class AcknowledgeMessage implements IMessage {

	private int xid;
	
	public AcknowledgeMessage(int xid) {
		this.xid = xid;
	}
	
	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public String getType() {
		return "Acknowledgement";
	}

}
