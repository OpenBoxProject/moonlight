package org.moonlightcontroller.managers.models.messages;

public class ReadRequest extends Message {

	private String blockId;
	private String readHandle;
	
	// Default constructor to support Jersy
	public ReadRequest() {}
	
	public ReadRequest(String block, String handle) {
		super(0);
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
