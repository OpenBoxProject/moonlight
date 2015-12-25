package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class HeaderPayloadClassifier extends ProcessingBlock {
	private compound_matches match;

	public HeaderPayloadClassifier(String id, compound_matches match) {
		super(id);
		this.match = match;
	}

	public compound_matches getMatch() {
		return match;
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

	@Override
	protected ProcessingBlock spawn(String id) {
		return new HeaderPayloadClassifier(id, match);
	}
}
class compound_matches {}