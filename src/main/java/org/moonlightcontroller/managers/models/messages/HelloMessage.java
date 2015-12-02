package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class HelloMessage implements IMessage {
	private String type;
	private int xid;
	private int dpid;
	private String version;
	private Map<String, List<String>> capabilities;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getXid() {
		return xid;
	}
	public void setXid(int xid) {
		this.xid = xid;
	}
	public int getDpid() {
		return dpid;
	}
	public void setDpid(int dpid) {
		this.dpid = dpid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(Map<String, List<String>> capabilities) {
		this.capabilities = capabilities;
	}
}