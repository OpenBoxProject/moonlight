package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class ToDevice extends ProcessingBlock {

	private String device;
	
	public ToDevice(String id, String device){
		super(id);
		this.device = device;
	}

	public String getDevice() {
		return device;
	}
}
