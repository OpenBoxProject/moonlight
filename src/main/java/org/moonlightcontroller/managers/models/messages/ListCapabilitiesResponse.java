package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class ListCapabilitiesResponse extends Message {
	private Map<String, List<String>> capabilities;
	
	// Default constructor to support Jersy
	public ListCapabilitiesResponse() {}
	
	
	public ListCapabilitiesResponse(int xid, Map<String, List<String>> capabilities) {
		super(xid);
		this.capabilities = capabilities;
	}
	
	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
}