package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class FromDevice extends ProcessingBlock {

	private int output;
	private String devname;
	private boolean sniffer;
	private boolean promisc;
	
	public int getOutputPort() {
		return this.output;
	}

	public String getDevname() {
		return devname;
	}

	public boolean isSniffer() {
		return sniffer;
	}

	public boolean isPromisc() {
		return promisc;
	}	
	
    protected FromDevice(Init<?> init) {
        super(init);
        this.output = init.output;
        this.devname = init.devname;
        this.sniffer = init.sniffer;
        this.promisc = init.promisc;
    }
    
	protected static abstract class Init<T extends Init<T>> extends ProcessingBlock.Init<T> {
		private int output;
		private String devname;
		private boolean sniffer;
		private boolean promisc;
		
		public T setDevice(String device){
			this.devname = device;
			return self();
		}
		
		public T setSniffer(boolean sniffer){
			this.sniffer = sniffer;
			return self();
		}
		
		public T setPromisc(boolean promisc){
			this.promisc = promisc;
			return self();
		}
		
		public FromDevice build(){
			this.addPort();
			return new FromDevice(this);
		}
	}
	
    public static class Builder extends Init<Builder> {
        @Override
        protected Builder self() {
            return this;
        }
    }
}
