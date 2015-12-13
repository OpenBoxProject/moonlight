package org.moonlightcontroller.processing;

import java.util.List;
import java.util.Map;

public interface IProcessingBlock {
	public String getId();
	
	public BlockClass getBlockClass();
	
	public List<Integer> getPorts();

	public String getBlockType();
	
	public String toShortString();
	
	public Map<String, String> getConfiguration();
	
	// Clone the block but give it a unique ID
	public IProcessingBlock clone();
	
	public interface Builder {
		public Builder addPort();
		public IProcessingBlock build();
	}
}
