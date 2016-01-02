package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hello extends Message {
	private int dpid;
	private String version;
	// TODO: capabilities is commented out because of a mismatch between the received json from the OBI and the spec
	// private Map<String, List<String>> capabilities;
	
	// Default constructor to support Jersy
	public Hello() {}
	
	public Hello(int xid, int dpid, String version) {	
		super(xid);
		this.dpid = dpid;
		this.version = version;
	}
	
	public int getDpid() {
		return dpid;
	}

	public String getVersion() {
		return version;
	}
	
/*
	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
	*/
}