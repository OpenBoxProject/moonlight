package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class ToDevice extends ProcessingBlock {

	private String devname;
	
	protected static abstract class Init<T extends Init<T>> extends ProcessingBlock.Init<T> {
		private String devname;
		
		public T setDevice(String device){
			this.devname = device;
			return self();
		}
		
		public ToDevice build(){
			return new ToDevice(this);
		}
	}

    public static class Builder extends Init<Builder> {
        @Override
        protected Builder self() {
            return this;
        }
    }
    
	public ToDevice(Init<?> init){
		super(init);
		this.devname = init.devname;
	}

	public String getDevice() {
		return this.devname;
	}
}
