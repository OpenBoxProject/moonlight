package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class Restore extends ProcessingBlock {
	private String key;
	private double offset;
	private String protocol;

	public Restore(String id, String key, double offset, String protocol) {
		super(id);
		this.key = key;
		this.offset = offset;
		this.protocol = protocol;
	}
	public String getKey() {
		return key;
	}
	public double getOffset() {
		return offset;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
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
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("key", this.key);
		config.put("offset", this.offset + "");
		config.put("protocol", this.protocol);
	}
}
