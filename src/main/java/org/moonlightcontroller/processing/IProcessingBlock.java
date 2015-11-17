package org.moonlightcontroller.processing;

import java.util.List;

public interface IProcessingBlock {

	List<Integer> getPorts();
	
	String getId();
	
	public interface Builder {
		
		Builder setId(String id);
		
		Builder addPort();
		
		IProcessingBlock build();
	}
}
