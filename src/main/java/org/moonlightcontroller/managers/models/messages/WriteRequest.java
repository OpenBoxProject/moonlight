package org.moonlightcontroller.managers.models.messages;


public class WriteRequest extends Message {

	private String blockId;
	private String writeHandle;
	private String value;

	// Default constructor to support Jersy
	public WriteRequest() {}

	public WriteRequest(int xid, String block, String handle, String value) {
		super(xid);
		this.blockId = block;
		this.writeHandle = handle;
		this.value = value;
	}

	public WriteRequest(String block, String handle, String value) {
		super(0);
		this.blockId = block;
		this.writeHandle = handle;
		this.value = value;
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