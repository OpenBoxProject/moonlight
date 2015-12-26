package org.moonlightcontroller.managers.models.messages;

public class GetParametersRequest extends Message {
	
	// Default constructor to support Jersy
	public GetParametersRequest() {}
	
	public GetParametersRequest(int xid) {
		super(xid);
	}
}
