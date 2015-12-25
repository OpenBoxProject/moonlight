package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.aggregator.UUIDGenerator;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;


public class Alert extends ProcessingBlock implements IStaticProcessingBlock {

	private String message;
	private int severity;
	private boolean attach_packet;
	private int packet_size;

	public Alert(String id, String message) {
		super(id);
		this.message = message;
	}

	public Alert(String id, String message, int severity, boolean attach_packet, int packet_size) {
		super(id);
		this.message = message;
		this.severity = severity;
		this.attach_packet = attach_packet;
		this.packet_size = packet_size;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_STATIC;
	}

	@Override
	public String getBlockType() {
		return this.getClass().getName();
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new Alert(id, this.message);
	}

	@Override
	public boolean canMergeWith(IStaticProcessingBlock other) {
		if (!(other instanceof Alert))
			return false;
		return true;
	}

	@Override
	public IStaticProcessingBlock mergeWith(IStaticProcessingBlock other) throws MergeException {
		if (other instanceof Alert) {
			Alert o = (Alert)other;
			return new Alert("MERGED##" + this.getId() + "##" + other.getId() + "##" + UUIDGenerator.getSystemInstance().getUUID().toString(), this.message + ";;" + o.message);
		} else {
			throw new MergeException("Cannot merge statics of different type");
		}
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("message", this.message);
		config.put("severity", this.severity+"");
		config.put("attach_packet", this.attach_packet? "true" : "false");
		config.put("packet_size", this.packet_size+"");

	}
}
