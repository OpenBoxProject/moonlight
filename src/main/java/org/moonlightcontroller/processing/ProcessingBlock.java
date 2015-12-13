package org.moonlightcontroller.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public Map<String, String> getConfiguration() {
		Map<String, String> config = new HashMap<String, String>();
		putConfiguration(config);
		return config;
	}
	
	protected abstract void putConfiguration(Map<String, String> config);
	
	@Override
	public abstract ProcessingBlock clone();
	
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
