package org.moonlightcontroller.processing;

import java.util.ArrayList;
import java.util.List;

public class ProcessingBlock implements IProcessingBlock{

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
	
	protected ProcessingBlock(Init<?> init){
		this.id = init.id;
		this.ports = init.ports;
		this.portCount = init.portCount;
	}
	
	public static abstract class Init<T extends Init<T>>{
		
		private String id;
		private List<Integer> ports;
		protected int portCount;
		
		protected abstract T self();
		
		protected Init(){
			this.portCount = 0;
			this.id = "";
			this.ports = new ArrayList<>();
		}
		
		public T setId(String id){
			this.id = id;
			return self();
		}

		public T addPort() {
			this.portCount++;
			this.ports.add(this.portCount);
			return self();
		}
		
		public ProcessingBlock build(){
			return new ProcessingBlock(this);
		}
	}
	
	public static class Builder extends Init<Builder> implements IProcessingBlock.Builder{
        @Override
        protected Builder self() {
            return this;
        }
	}
}
