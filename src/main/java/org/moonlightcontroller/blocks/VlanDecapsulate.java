package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class VlanDecapsulate extends ProcessingBlock {

	public VlanDecapsulate(String id) {
		super(id);
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
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new VlanDecapsulate(id);
		
	}
}
