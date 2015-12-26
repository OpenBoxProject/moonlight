package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class Discard extends ProcessingBlock {
	
	public Discard(String id) {
		super(id);
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		// No config for 'Discard'
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new Discard(id);
	}
}
