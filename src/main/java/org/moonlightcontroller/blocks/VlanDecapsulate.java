package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class VlanDecapsulate extends ProcessingBlock {

	public VlanDecapsulate(String id) {
		super(id);
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new VlanDecapsulate(id);
		
	}
}
