package org.moonlightcontroller.managers.models.messages;

public class Error implements IResponseMessage {

	private int xid;
	private String type; 
	private String error_type;
	private String error_subtype;
	private String message;
	private String extended_message;
	
	// Default constructor to support Jersy
	public Error() {}
		
	public Error (int xid, String error_type, String error_subtype, String message, String extended_message) {
		this.xid = xid;
		this.type = this.getClass().getName();
		this.error_type = error_type;
		this.error_subtype = error_subtype;
		this.message = message;
		this.extended_message = extended_message;
	}
	
	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setXid(int xid) {
		
	}

	public String getError_type() {
		return error_type;
	}

	public String getError_subtype() {
		return error_subtype;
	}

	public String getMessage() {
		return message;
	}

	public String getExtended_message() {
		return extended_message;
	}
}
