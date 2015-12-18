package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class EncapsulateMetadata extends ProcessingBlock {

	public EncapsulateMetadata(String id) {
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
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
	}
}
