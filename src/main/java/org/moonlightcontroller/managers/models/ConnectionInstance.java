package org.moonlightcontroller.managers.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.moonlightcontroller.main.ControllerProperties;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.southbound.client.SingleInstanceConnection;

public class ConnectionInstance implements IConnectionInstance {

	private long dpid;
	private LocalDateTime lastKeepAlive;
	private String version;
	private int keepaliveInterval;
	private Map<String, List<String>> capabilities;
	private boolean isProcessingGraphConfiged;
	private SingleInstanceConnection client;
	private String ip;
	
	public ConnectionInstance (long dpid,
			String version,
			int keepaliveInterval,
			Map<String, List<String>> capabilities,
			String ip) {
		this.setDpid(dpid);
		this.version = version;
		this.keepaliveInterval = keepaliveInterval;
		this.capabilities = capabilities;
		this.setProcessingGraphConfiged(false);
		this.ip = ip;
		this.client = new SingleInstanceConnection(this.ip, 3636);
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

	public long getDpid() {
		return dpid;
	}

	public void setDpid(long dpid) {
		this.dpid = dpid;
	}

	public String getVersion() {
		return version;
	}

	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}

	public boolean isProcessingGraphConfiged() {
		return isProcessingGraphConfiged;
	}

	public void setProcessingGraphConfiged(boolean isProcessingGraphConfiged) {
		this.isProcessingGraphConfiged = isProcessingGraphConfiged;
	}

	public static class Builder {
		private final int DEFAULT_KEEPALIVE_DURATION = ControllerProperties.getInstance().getKeepAliveInterval();

		long dpid;
		int keepaliveInterval = DEFAULT_KEEPALIVE_DURATION;
		LocalDateTime lastKeepAlive;
		String version = "";
		String ip;
		Map<String, List<String>> capabilities = new HashMap<>();

		public Builder() {

		}

		public Builder setIp(String ip) {
			this.ip = ip;
			return this;
		}

		public Builder setDpid(long dpid) {
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
					capabilities,
					ip);
		}
	}

	@Override
	public void sendRequest(IMessage message, IRequestSender requestSender) {
		client.sendMessage(message);
	}
}
