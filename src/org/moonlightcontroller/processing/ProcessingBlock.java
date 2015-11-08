package org.moonlightcontroller.processing;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessingBlock {

	private String id;
	private List<Integer> ports;
	private int portCount;
	
	protected ProcessingBlock(String id) {
		this.portCount = 0;
		this.ports = new ArrayList<>();
		this.id = id;
	}
	
	protected int addPort(){
		this.portCount++;
		this.ports.add(this.portCount);
		return this.portCount;
	}
	
	public String getId() {
		return this.id;
	}
}
