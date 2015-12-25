package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class Queue extends ProcessingBlock {
	private int capacity;

	public Queue(String id) {
		super(id);
	}
	
	public Queue(String id, int capacity) {
		super(id);
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public String getBlockType() {
		return this.getClass().getName();
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_SHAPER;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("capacity", this.capacity+ "");
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new Queue(id, capacity);
	}
}
