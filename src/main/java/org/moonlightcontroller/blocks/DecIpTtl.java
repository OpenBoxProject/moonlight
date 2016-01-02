package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class DecIpTtl extends ProcessingBlock {
	private boolean active;

	public DecIpTtl(String id) {
		super(id);
	}
	
	public DecIpTtl(String id, boolean active) {
		super(id);
		this.active = active;
	}

	public boolean getActive() {
		return active;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {
		config.put("active", this.active);
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new DecIpTtl(id, this.active);
	}
}
