package org.moonlightcontroller.managers.models.messages;

import java.util.Map;

public class GetParametersResponse implements IMessage {

	private String type;
	private int xid;
	private Map<String, String> parameters;
	
	// Default constructor to support Jersy
	public GetParametersResponse() {}

	public GetParametersResponse (int xid, Map<String, String> parameters) {
		this.type = this.getClass().getName();
		this.xid = xid;
		this.parameters = parameters;
	}
	
	@Override
	public int getXid() {
		return xid;
	}
	
	@Override
	public void setXid(int xid) {
		this.xid = xid;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
}
