package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class Hello extends Message {
	private int dpid;
	private String version;
	private Map<String, List<String>> capabilities;
	
	// Default constructor to support Jersy
	public Hello() {}
	
	public Hello(int xid, int dpid, String version, Map<String, List<String>> capabilities) {
		super(xid);
		this.dpid = dpid;
		this.version = version;
		this.capabilities = capabilities;
	}
	
	public int getDpid() {
		return dpid;
	}

	public String getVersion() {
		return version;
	}

	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
}