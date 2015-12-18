package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class ProtocolAnalyzer extends ProcessingBlock {
	private String[] protocol;

	public ProtocolAnalyzer(String id, String[] protocol) {
		super(id);
		this.protocol = protocol;
	}
	public String[] getProtocol() {
		return protocol;
	}
	public void setProtocol(String[] protocol) {
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
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("protocol", this.protocol.toString());
	}
}
