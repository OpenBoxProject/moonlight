package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class ListCapabilitiesResponse implements IMessage {
	private String type;
	private int xid;
	private Map<String, List<String>> capabilities;
	
	// Default constructor to support Jersy
	public ListCapabilitiesResponse() {}
	
	
	public ListCapabilitiesResponse(int xid, Map<String, List<String>> capabilities) {
		this.type = this.getClass().getName();
		this.xid = xid;
		this.capabilities = capabilities;
	}
	
	public String getType() {
		return type;
	}

	public int getXid() {
		return xid;
	}
	
	@Override
	public void setXid(int xid) {
		this.xid = xid;
	}

	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
}