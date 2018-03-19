package org.moonlightcontroller.managers.models.messages;

public class ReadRequest extends Message {

	private String blockId;
	private String readHandle = "stub";
	
	// Default constructor to support Jersy
	public ReadRequest() {
		super();
	}

	public ReadRequest(int xid, String block) {
		super(xid);
		this.blockId = block;
	}

	public ReadRequest(String block, String handle) {
		super();
		this.blockId = block;
		this.readHandle = handle;
	}

	public String getBlockId() {
		return blockId;
	}

	public String getReadHandle() {
		return readHandle;
	}
}
