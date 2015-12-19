package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class RegexMatcher extends ProcessingBlock {
	private String[] pattern;
	private boolean payload_only;
	private boolean match_all;

	public RegexMatcher(String id, String[] pattern, boolean payload_only, boolean match_all) {
		super(id);
		this.pattern = pattern;
		this.payload_only = payload_only;
		this.match_all = match_all;
	}

	public String[] getPattern() {
		return pattern;
	}

	public boolean getPayload_only() {
		return payload_only;
	}

	public boolean getMatch_all() {
		return match_all;
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
		config.put("match_all", this.match_all? "true" : "false");
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new RegexMatcher(id, pattern, payload_only, match_all);
	}
}
