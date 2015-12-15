package org.moonlightcontroller.aggregator.temp;

public interface IProcessingBlock {
	public String getId();
	
	public BlockClass getBlockClass();
	
	public String getBlockType();
	
	public String toShortString();
	
	// Clone the block but give it a unique ID
	public IProcessingBlock clone();
	
	public IProcessingBlock getOriginalInstance();
	
	public interface Builder {
		public Builder setBlockType(BlockClass type);
		public IProcessingBlock build();
	}
}
