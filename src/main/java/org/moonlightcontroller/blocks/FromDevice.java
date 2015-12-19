package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class FromDevice extends ProcessingBlock {

	private int output;
	private String devname;
	private boolean sniffer;
	private boolean promisc;
	
	public FromDevice(String id, int output, String devname, boolean sniffer, boolean promisc) {
		super(id);
		this.output = output;
		this.devname = devname;
		this.sniffer = sniffer;
		this.promisc = promisc;
		this.addPort();
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

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}

	@Override
	public String getBlockType() {
		// TODO Auto-generated method stub
		return null;
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
	
}
