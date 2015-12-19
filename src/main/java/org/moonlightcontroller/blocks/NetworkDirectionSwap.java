package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class NetworkDirectionSwap extends ProcessingBlock {
	private boolean ethernet;
	private boolean ipv4;
	private boolean ipv6;
	private boolean tcp;
	private boolean udp;

	public NetworkDirectionSwap(String id, boolean ethernet, boolean ipv4, boolean ipv6, boolean tcp, boolean udp) {
		super(id);
		this.ethernet = ethernet;
		this.ipv4 = ipv4;
		this.ipv6 = ipv6;
		this.tcp = tcp;
		this.udp = udp;
	}

	public boolean getEthernet() {
		return ethernet;
	}

	public boolean getIpv4() {
		return ipv4;
	}

	public boolean getIpv6() {
		return ipv6;
	}

	public boolean getTcp() {
		return tcp;
	}

	public boolean getUdp() {
		return udp;
	}

	@Override
	public String getBlockType() {
		return null;
	}

	@Override
	public String toShortString() {
		return null;
	}

	@Override
	public ProcessingBlock clone() {
		return null;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("ethernet", this.ethernet? "true" : "false");
		config.put("ipv4", this.ipv4? "true" : "false");
		config.put("ipv6", this.ipv6? "true" : "false");
		config.put("tcp", this.tcp? "true" : "false");
		config.put("udp", this.udp? "true" : "false");
	}
}
