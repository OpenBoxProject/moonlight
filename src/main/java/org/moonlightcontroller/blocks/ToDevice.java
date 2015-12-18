package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class ToDevice extends AbstractProcessingBlock {

	private String devname;

	public ToDevice(String id, String devname){
		super(id);
		this.devname = devname;
	}

	public String getDevice() {
		return this.devname;
	}

	public static class Builder extends ProcessingBlock.Builder {
		private String devname;
		
		public Builder setDevice(String device){
			this.devname = device;
			return this;
		}
		
		public ToDevice build(){
			return new ToDevice(super.id, this.devname);
		}
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}

	@Override
	public String getBlockType() {
		return "ToDevice"
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
		config.put("devname", devname);
	}
    
	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new ToDevice(id);
	}
}
