package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class SessionClassifier extends ProcessingBlock {
	private Map<String, Boolean> match;

	public SessionClassifier(String id, Map<String, Boolean> match) {
		super(id);
		this.match = match;
	}
	public Map<String, Boolean> getMatch() {
		return match;
	}
	public void setMatch(Map<String, Boolean> match) {
		this.match = match;
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
		config.put("match", this.match.toString());
	}
}
