package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hello extends Message {
	private long dpid;
	private String version;
	private Map<String, List<String>> capabilities;
	
	// Default constructor to support Jersy
	public Hello() {}
	
	public Hello(int xid, long dpid, String version, Map<String, List<String>> capabilities) {
		super(xid);
		this.dpid = dpid;
		this.version = version;
		this.capabilities = capabilities;
	}
	
	public long getDpid() {
		return dpid;
	}

	public String getVersion() {
		return version;
	}

	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
}