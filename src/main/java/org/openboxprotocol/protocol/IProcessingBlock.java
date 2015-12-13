package org.openboxprotocol.protocol;

import java.util.List;

import org.moonlightcontroller.processing.BlockClass;

public interface IProcessingBlock {
	public String getId();
	
	public BlockClass getBlockClass();
	
	public List<Integer> getPorts();

	public String getBlockType();
	
	public String toShortString();
	
	// Clone the block but give it a unique ID
	public IProcessingBlock clone();
	
	public interface Builder {
		public Builder addPort();
		public Builder setBlockType(BlockClass type);
		public IProcessingBlock build();
	}
}
