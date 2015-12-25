package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class Hello implements IMessage {
	public String type;
	public int xid;
	public int dpid;
	public String version;
	public Map<String, List<String>> capabilities;
	
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

	public int getXid() {
		return xid;
	}
	
	@Override
	public void setXid(int xid) {
		this.xid = xid;
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