package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;

public class FromDevice extends ProcessingBlock {

	private int output;
	private String devname;
	private boolean sniffer;
	private boolean promisc;
	
	public FromDevice(String id, String device, boolean sniffer, boolean promisc) {
		super(id);
		this.output = this.addPort();
		this.devname = device;
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
}
