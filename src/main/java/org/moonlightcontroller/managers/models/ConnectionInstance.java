package org.moonlightcontroller.managers.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.moonlightcontroller.main.ControllerProperties;
import org.moonlightcontroller.managers.ClientConnectionManager;
import org.moonlightcontroller.managers.XidGenerator;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphMessage;

public class ConnectionInstance implements IConnectionInstance {

	private int dpid;
	private LocalDateTime lastKeepAlive;
	private String version;
	private int keepaliveInterval;
	private Map<String, List<String>> capabilities;
	private boolean isProcessingGraphConfiged;
	
	public ConnectionInstance (int dpid,
			String version,
			int keepaliveInterval,
			Map<String, List<String>> capabilities) {
		this.setDpid(dpid);
		this.setVersion(version);
		this.keepaliveInterval = keepaliveInterval;
		this.setCapabilities(capabilities);
		this.setProcessingGraphConfiged(false);
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

	public boolean isProcessingGraphConfiged() {
		return isProcessingGraphConfiged;
	}

	public void setProcessingGraphConfiged(boolean isProcessingGraphConfiged) {
		this.isProcessingGraphConfiged = isProcessingGraphConfiged;
	}

	public static class Builder {
		private final int DEFAULT_KEEPALIVE_DURATION = ControllerProperties.getInstance().getKeepAliveInterval();

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

	@Override
	public void sendRequest(IMessage message, IRequestSender requestSender) {
		int xid = XidGenerator.generateXid();
		if (message instanceof SetProcessingGraphMessage) {
			ClientConnectionManager.getInstance().sendProcessingGraphRequest(xid);
		}
	}
}
