package org.moonlightcontroller.managers.models.messages;

public class WriteResponse implements IMessage {

	private String type;
	private int xid;
	private String blockId;
	private String writeHandle;

	// Default constructor to support Jersy
	public WriteResponse() {}

	public WriteResponse(int xid, String blockId, String writeHandle) {
		this.type = this.getClass().getName();
		this.xid = xid;
		this.blockId = blockId;
		this.writeHandle = writeHandle;
	}

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
	@Override
	public void setXid(int xid) {

	}
}
