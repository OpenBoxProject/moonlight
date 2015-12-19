package org.moonlightcontroller.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.moonlightcontroller.aggregator.UUIDGenerator;

public abstract class ProcessingBlock implements IProcessingBlock{

	private String id;
	private List<Integer> ports;
	private int portCount;
	
	private boolean cloned = false;
	private IProcessingBlock original = null;

	protected abstract ProcessingBlock spawn(String id);

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
	public IProcessingBlock clone() {
		String newId;
		if (this.cloned) {
			newId = String.format("%s::%s", this.getId().substring(0, this.getId().indexOf("::")), UUIDGenerator.getSystemInstance().getUUID().toString());
		} else {
			newId = String.format("%s::%s", this.getId(), UUIDGenerator.getSystemInstance().getUUID().toString());
		}
		ProcessingBlock clone = this.spawn(newId);
		if (this.cloned) {
			clone.original = this.original;
		} else {
			clone.original = this;
		}
		clone.cloned = true;
		
		return clone;
	}
	
	@Override
	public boolean isClone() {
		return this.cloned;
	}
	
	@Override
	public IProcessingBlock getOriginalInstance() {
		return this.original;
	}
	
	@Override
	public String toString() {
		return String.format("[Block type: %s, id: %s]", this.getBlockType(), this.getId());
	}
	
	public String toShortString() {
		return String.format("[B id: %s]", this.getId());
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
