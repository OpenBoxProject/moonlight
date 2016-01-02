package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;

public class FromDevice extends ProcessingBlock {

	private String devname;
	private boolean sniffer;
	private boolean promisc;
	
	public FromDevice(String id, String devname) {
		super(id);
		this.devname = devname;
		this.addPort();
	}
	
	public FromDevice(String id, String devname, boolean sniffer, boolean promisc) {
		super(id);
		this.devname = devname;
		this.sniffer = sniffer;
		this.promisc = promisc;
		this.addPort();
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
	protected void putConfiguration(Map<String, Object> config) {
		config.put("devname", this.devname);
		config.put("sniffer", this.sniffer);
		config.put("promisc", this.promisc);
	}
	
	@Override
	protected ProcessingBlock spawn(String id) {
		return new FromDevice(id, this.devname, this.sniffer, this.promisc);
	}
}
