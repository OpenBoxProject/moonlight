package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class NetworkHeaderFieldsRewriter extends ProcessingBlock {

	private NetworkHeaderFieldsRewriter(String id) {
		super(id);
	}
	
	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	public String getBlockType() {
		return "NetworkHeaderFieldsRewriter";
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new NetworkHeaderFieldsRewriter(id);
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		// No config for 'NetworkHeaderFieldsRewriter'
	}
	
	public static class Builder extends ProcessingBlock.Builder {
		@Override
		public NetworkHeaderFieldsRewriter build(){
			this.addPort();
			return new NetworkHeaderFieldsRewriter(super.id);
		}
	}
}
