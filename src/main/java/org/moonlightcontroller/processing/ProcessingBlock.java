package org.moonlightcontroller.processing;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessingBlock implements IProcessingBlock{

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
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public List<Integer> getPorts() {
		return this.ports;
	}
	
	public static abstract class Builder implements IProcessingBlock.Builder {
		
		protected String id;
		protected List<Integer> ports;
		protected int portCount;
				
		protected Builder(){
			this.portCount = 0;
			this.id = "";
			this.ports = new ArrayList<>();
		}
		
		public Builder setId(String id){
			this.id = id;
			return this;
		}

		public Builder addPort() {
			this.portCount++;
			this.ports.add(this.portCount);
			return this;
		}
		
		public abstract ProcessingBlock build();
	}
	
}
