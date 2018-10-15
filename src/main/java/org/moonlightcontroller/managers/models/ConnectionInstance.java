package org.moonlightcontroller.managers.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.moonlightcontroller.main.ControllerProperties;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.southbound.client.SingleInstanceConnection;
import org.moonlightcontroller.southbound.server.SouthboundServer;
import org.openbox.dashboard.SouthboundProfiler;

/**
 * Connection Instance is class responsible of connection with OBIs 
 * It implements the IConnectionInstance interface which enables sending messages to OBIs
 */
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
		String[] ipPort = ip.split(":");
		this.ip = ip;

		int port = 3636;

		if (ipPort.length == 2)
			port = Integer.valueOf(ipPort[1]);

		this.client = new SingleInstanceConnection(ipPort[0], port, SouthboundServer.SERVER_HOST,  SouthboundServer.SERVER_PORT);
	}

	/**
	 * Updates the last keepalive update to Now
	 */
	public void updateKeepAlive() {
		this.lastKeepAlive = LocalDateTime.now();
	}

	/**
	 * @return the last keep alive time
	 */
	public LocalDateTime getKeepAliveDate() {
		return this.lastKeepAlive;
	}

	/**
	 * @return the keep alive interval
	 */
	public int getKeepAliveInterval() {
		return this.keepaliveInterval;
	}

	/**
	 * @return the OBI dpid for this connection
	 */
	public long getDpid() {
		return dpid;
	}

	/**
	 * Sets the dpid for this connection
	 * @param dpid
	 */
	public void setDpid(long dpid) {
		this.dpid = dpid;
	}

	/**
	 * @return the version of the connection
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the capabilities of the OBI for this connection
	 */
	public Map<String, List<String>> getCapabilities() {
		return capabilities;
	}

	/**
	 * @return whether a processing graph was configured for this OBI
	 */
	public boolean isProcessingGraphConfiged() {
		return isProcessingGraphConfiged;
	}

	/**
	 * Sets the processing graph configured flag
	 * @param isProcessingGraphConfiged
	 */
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
		SouthboundProfiler.getInstance().onMessage(message, false);

	}
}
