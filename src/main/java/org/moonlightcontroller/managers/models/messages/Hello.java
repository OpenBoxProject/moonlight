package org.moonlightcontroller.managers.models.messages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello implements IMessage {
	private String type;
	private int xid;
	private int dpid;
	private String version;
	private Map<String, List<String>> capabilities;
	
	public Hello() {
	}
	
	public Hello(int xid, int dpid, String version, Map<String, List<String>> capabilities) {
		this.type = "Hello";
		this.xid = xid;
		this.dpid = dpid;
		this.version = version;
		this.capabilities = capabilities;
	}
	
	@Override
	public String toString() {
		return String.format(
				"HelloMessage::xid=%d::dpid=%d::version=%s::cap=%s",
				this.xid, this.dpid, this.version, this.capabilities);
	}
	public String getType() {
		return type;
	}
	public int getXid() {
		return xid;
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
	
	public static class Builder {
		private int xid;
		private int dpid;
		private String version;
		private Map<String, List<String>> capabilities = new HashMap<>();
		
		public Builder setXid(int xid) {
			this.xid = xid;
			return this;
		}
		public Builder setDpid(int dpid) {
			this.dpid = dpid;
			return this;
		}
		public Builder setVersion(String version) {
			this.version = version;
			return this;
		}
		public Builder setCapabilities(Map<String, List<String>> capabilities) {
			this.capabilities = capabilities;
			return this;
		}
		
		public Hello build() {
			return new Hello(xid, dpid, version, capabilities);
		}
	}
}