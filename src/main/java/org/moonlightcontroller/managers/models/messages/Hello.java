package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

import org.moonlightcontroller.blocks.ObiType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hello extends Message {
	private String version;
	private Map<String, List<String>> capabilities;
	private ObiType obitype;
	
	// Default constructor to support Jersey
	public Hello() {}
	
	public Hello(int xid, long dpid, String version, Map<String, List<String>> capabilities, ObiType obitype) {
		super(xid);
		setDpid(dpid);
		this.version = version;
		this.capabilities = capabilities;
		this.obitype = obitype;
	}

	public String getVersion() {
		return version;
	}

	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
	
	public ObiType getObiType(){
		return obitype;
	}
}