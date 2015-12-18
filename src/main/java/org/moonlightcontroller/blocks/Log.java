package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class Log extends ProcessingBlock {
	private String message;
	private int severity;
	private boolean attach_packet;
	private int packet_size;

	public Log(String id, String message, int severity, boolean attach_packet, int packet_size) {
		super(id);
		this.message = message;
		this.severity = severity;
		this.attach_packet = attach_packet;
		this.packet_size = packet_size;
	}
	public String getMessage() {
		return message;
	}
	public int getSeverity() {
		return severity;
	}
	public boolean getAttach_packet() {
		return attach_packet;
	}
	public int getPacket_size() {
		return packet_size;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public void setAttach_packet(boolean attach_packet) {
		this.attach_packet = attach_packet;
	}
	public void setPacket_size(int packet_size) {
		this.packet_size = packet_size;
	}
	@Override
	public String getBlockType() {
		return null;
	}
	@Override
	public String toShortString() {
		return null;
	}
	@Override
	public ProcessingBlock clone() {
		return null;
	}
	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_STATIC;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("message", this.message);
		config.put("severity", this.severity+ "");
		config.put("attach_packet", this.attach_packet? "true" : "false");
		config.put("packet_size", this.packet_size+ "");
	}
}
