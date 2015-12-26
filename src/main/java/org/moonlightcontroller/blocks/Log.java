package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;

import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;

public class Log extends ProcessingBlock implements IStaticProcessingBlock{
	private String message;
	private int severity;
	private boolean attach_packet;
	private int packet_size;

	public Log(String id, String message) {
		super(id);
		this.message = message;
	}
	
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

	@Override
	protected ProcessingBlock spawn(String id) {
		return new Log(id, message, severity, attach_packet, packet_size);
	}

	@Override
	public boolean canMergeWith(IStaticProcessingBlock other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IStaticProcessingBlock mergeWith(IStaticProcessingBlock other) throws MergeException {
		// TODO Auto-generated method stub
		return null;
	}
}
