package org.moonlightcontroller.managers.models.messages;

public class ReadResponse implements IMessage {
	
	private String type;
	private int xid;
	private String blockId;	
	private String readHandle;
	private String result;
	
	public String getType() {
		return type;
	}
	public int getXid() {
		return xid;
	}
	public String getBlockId() {
		return blockId;
	}
	public String getReadHandle() {
		return readHandle;
	}
	public String getResult() {
		return result;
	}
}
