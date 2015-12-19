package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class Hello implements IMessage {
	private String type;
	private int xid;
	private int dpid;
	private String version;
	private Map<String, List<String>> capabilities;
	
	// Default constructor to support Jersy
	public Hello() {}
	
	
	public Hello(int xid, int dpid, String version, Map<String, List<String>> capabilities) {
		this.type = this.getClass().getName();
		this.xid = xid;
		this.dpid = dpid;
		this.version = version;
		this.capabilities = capabilities;
	}
	
	@Override
	public String toString() {
		return String.format(
				"HelloMessage::xid=%d::dpid=%d::version=%s::cap=%s",
				this.getXid(), this.dpid, this.version, this.capabilities);
	}

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