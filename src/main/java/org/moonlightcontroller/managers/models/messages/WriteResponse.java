package org.moonlightcontroller.managers.models.messages;

public class WriteResponse extends Message {

	private String blockId;
	private String writeHandle;

	// Default constructor to support Jersy
	public WriteResponse() {}

	public WriteResponse(int xid, String blockId, String writeHandle) {
		super(xid);
		this.blockId = blockId;
		this.writeHandle = writeHandle;
	}
	
	public String getBlockId() {
		return blockId;
	}
	
	public String getWriteHandle() {
		return writeHandle;
	}
}