package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class Discard extends AbstractProcessingBlock {
	private Discard(String id) {
		super(id);
	}
	
	public static class Builder extends ProcessingBlock.Builder {			
		@Override
		public Discard build(){
			this.addPort();
			return new Discard(super.id);
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}

	@Override
	public String getBlockType() {
		return "Discard";
	}

	@Override
	public String toShortString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessingBlock clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		// No config for 'Discard'
	}

	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new Discard(id);
	}
}
