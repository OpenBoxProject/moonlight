package org.moonlightcontroller.managers.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionInstance {

	long dpid;
	LocalDateTime lastKeepAlive;
	String version;
	int keepaliveInterval;
	Map<String, List<String>> capabilities;

	public ConnectionInstance (int dpid,
			String version,
			int keepaliveInterval,
			Map<String, List<String>> capabilities) {
		this.dpid = dpid;
		this.version = version;
		this.keepaliveInterval = keepaliveInterval;
		this.capabilities = capabilities;
	}

	public void updateKeepAlive() {
		this.lastKeepAlive = LocalDateTime.now();
	}

	public LocalDateTime getKeepAliveDate() {
		return this.lastKeepAlive;
	}

	public int getKeepAliveInterval() {
		return this.keepaliveInterval;
	}

	public static class Builder {
		private final int DEFAULT_KEEPALIVE_DURATION = 10;

		int dpid;
		int keepaliveInterval = DEFAULT_KEEPALIVE_DURATION;
		LocalDateTime lastKeepAlive;
		String version = "";
		Map<String, List<String>> capabilities = new HashMap<>();

		public Builder() {
			
		}

		public Builder setDpid(int dpid) {
			this.dpid = dpid;
			return this;
		}

		public Builder setKeepAliveInterval(int keepaliveInterval) {
			this.keepaliveInterval = keepaliveInterval;
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

		public ConnectionInstance build () {
			return new ConnectionInstance(dpid, 
					version,
					keepaliveInterval, 
					capabilities);
		}
	}
}
