package org.moonlightcontroller.managers.models.messages;


public class WriteRequest implements IMessage {

	private String type;
	private int xid;
	private String blockId;
	private String writeHandle;
	private String value;
	
	public WriteRequest(String block, String handle, String value) {
		this.type = this.getClass().getName();
		this.blockId = block;
		this.writeHandle = handle;
		this.value = value;
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

	public String getWriteHandle() {
		return writeHandle;
	}

	public String getValue() {
		return value;
	}
}
