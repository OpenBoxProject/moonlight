package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class FrontDropQueue extends ProcessingBlock {
	private double capacity;

	public FrontDropQueue(String id, double capacity) {
		super(id);
		this.capacity = capacity;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
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
		config.put("capacity", this.capacity + "");
	}
}
