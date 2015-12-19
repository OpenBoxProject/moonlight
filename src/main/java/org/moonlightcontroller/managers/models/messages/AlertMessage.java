package org.moonlightcontroller.managers.models.messages;

public class AlertMessage {
	private int id;
	private long timestamp;
	private String message;
	private int severity;
	private String packet;
	private String origin_block;
	
	// Default constructor to support Jersy
	public AlertMessage() {}
	
	public AlertMessage(int id, 
			long timestamp, 
			String message,
			int severity,
			String packet,
			String origin_block) {
		this.id = id;
		this.timestamp = timestamp;
		this.message = message;
		this.severity = severity;
		this.packet = packet;
		this.origin_block = origin_block;
	}
	
	public int getId() {
		return id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public int getSeverity() {
		return severity;
	}

	public String getPacket() {
		return packet;
	}

	public String getOrigin_block() {
		return origin_block;
	}
}
