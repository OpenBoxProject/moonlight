package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class BpsRateClassifier extends ProcessingBlock {
	private String[] rate;

	public BpsRateClassifier(String id, String[] rate) {
		super(id);
		this.rate = rate;
	}
	public String[] getRate() {
		return rate;
	}
	public void setRate(String[] rate) {
		this.rate = rate;
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
		config.put("rate", this.rate.toString());
	}
}
