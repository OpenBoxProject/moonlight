package org.moonlightcontroller.managers.models.messages;

public class ReadRequest implements IMessage {

	private String type;
	private int xid;
	private String blockId;
	private String readHandle;
	
	public ReadRequest(String block, String handle) {
		this.type = this.getClass().getName();
		this.blockId = block;
		this.readHandle = handle;
	}

	public String getType() {
		return type;
	}
	
	public int getXid() {
		return xid;
	}

	public void setXid(int xid) {
		this.xid = xid;
	}

	public String getBlockId() {
		return blockId;
	}

	public String getReadHandle() {
		return readHandle;
	}
}
