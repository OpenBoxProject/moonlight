package org.moonlightcontroller.processing;

import java.util.List;

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
		public IProcessingBlock build();
	}
}
