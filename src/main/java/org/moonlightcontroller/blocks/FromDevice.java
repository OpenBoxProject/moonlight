package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class FromDevice extends AbstractProcessingBlock {

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

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}

	@Override
	public String getBlockType() {
		return "FromDevice"
	}

	@Override
	public String toShortString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessingBlock clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("devname", this.devname);
		config.put("sniffer", this.sniffer ? "true" : "false");
		config.put("promisc", this.promisc ? "true" : "false");
	}
	
	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new FromDevice(id);
	}
}
