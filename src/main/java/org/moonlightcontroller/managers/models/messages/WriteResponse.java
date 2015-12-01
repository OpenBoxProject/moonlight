package org.moonlightcontroller.managers.models.messages;

public class WriteResponse implements IMessage {
	
	private String type;
	private int xid;
	private String blockId;
	private String writeHandle;
	
	public String getType() {
		return type;
	}
	public int getXid() {
		return xid;
	}
	public String getBlockId() {
		return blockId;
	}
	public String getWriteHandle() {
		return writeHandle;
	}
}
