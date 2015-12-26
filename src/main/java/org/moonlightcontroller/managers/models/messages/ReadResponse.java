package org.moonlightcontroller.managers.models.messages;

public class ReadResponse extends Message {
	
	private String blockId;	
	private String readHandle;
	private String result;
	
	// Default constructor to support Jersy
	public ReadResponse () {}
	
	public ReadResponse(int xid, String blockId, String readHandle, String result) {
		super(xid);
		this.blockId = blockId;
		this.readHandle = readHandle;
		this.result = result;
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
