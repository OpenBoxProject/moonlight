package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class Discard extends ProcessingBlock {
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
}
