package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class FromDevice extends ProcessingBlock {

	private int output;
	private String devname;
	private boolean sniffer;
	private boolean promisc;
	
	private FromDevice(String id, int output, String devname, boolean sniffer, boolean promisc) {
		super(id);
		this.output = output;
		this.devname = devname;
		this.sniffer = sniffer;
		this.promisc = promisc;
	}
	
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
	
	public static class Builder extends ProcessingBlock.Builder {
		private int output;
		private String devname;
		private boolean sniffer;
		private boolean promisc;
		
		public Builder setDevice(String device){
			this.devname = device;
			return this;
		}
		
		public Builder setSniffer(boolean sniffer){
			this.sniffer = sniffer;
			return this;
		}
		
		public Builder setPromisc(boolean promisc){
			this.promisc = promisc;
			return this;
		}
	
		@Override
		public FromDevice build(){
			this.addPort();
			return new FromDevice(super.id, this.output, this.devname, this.sniffer, this.promisc);
		}
	}
	
}
