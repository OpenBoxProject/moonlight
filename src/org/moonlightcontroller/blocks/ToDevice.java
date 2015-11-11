package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class ToDevice extends ProcessingBlock {

	private String devname;

	public ToDevice(String id, String devname){
		super(id);
		this.devname = devname;
	}

	public String getDevice() {
		return this.devname;
	}

	protected static class Builder extends ProcessingBlock.Builder {
		private String devname;
		
		public Builder setDevice(String device){
			this.devname = device;
			return this;
		}
		
		public ToDevice build(){
			return new ToDevice(super.id, this.devname);
		}
	}
    
}
