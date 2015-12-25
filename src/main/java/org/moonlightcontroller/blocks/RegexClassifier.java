package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class RegexClassifier extends ProcessingBlock {
	private String[] pattern;
	private boolean payload_only;

	public RegexClassifier(String id, String[] pattern, boolean payload_only) {
		super(id);
		this.pattern = pattern;
		this.payload_only = payload_only;
	}

	public String[] getPattern() {
		return pattern;
	}

	public boolean getPayload_only() {
		return payload_only;
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
		config.put("pattern", this.pattern.toString());
		config.put("payload_only", this.payload_only? "true" : "false");
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new RegexClassifier(id, pattern, payload_only);
	}
}
