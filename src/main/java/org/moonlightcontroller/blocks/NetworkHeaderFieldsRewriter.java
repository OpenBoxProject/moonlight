package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.types.MacAddress;

public class NetworkHeaderFieldsRewriter extends ProcessingBlock {

	private MacAddress eth_src;
	private MacAddress eth_dst;
	private int eth_type;
	private int ipv4_proto;
	private int ipv4_dscp;
	private int ipv4_ecn;
	private int ipv4_ttl;
	private int ipv4_src;
	private int ipv4_dst;
	private int tcp_src;
	private int tcp_dst;
	private int udp_src;
	private int udp_dst;
	
	public NetworkHeaderFieldsRewriter(String id) {
		super(id);
		this.addPort();
	}
	
	public NetworkHeaderFieldsRewriter(String id, MacAddress eth_src, MacAddress eth_dst, int eth_type, 
			int ipv4_proto,	int ipv4_dscp, int ipv4_ecn, int ipv4_ttl, int ipv4_src, int ipv4_dst, 
			int tcp_src, int tcp_dst, int udp_src, int udp_dst) {
		super(id);
		this.eth_src = eth_src;
		this.eth_dst = eth_dst;
		this.eth_type = eth_type;
		this.ipv4_proto = ipv4_proto;
		this.ipv4_dscp = ipv4_dscp;
		this.ipv4_ecn = ipv4_ecn;
		this.ipv4_ttl = ipv4_ttl;
		this.ipv4_src = ipv4_src;
		this.ipv4_dst = ipv4_dst;
		this.tcp_src = tcp_src;
		this.tcp_dst = tcp_dst;
		this.udp_src = udp_src;
		this.udp_dst = udp_dst;
		
		this.addPort();
	}
	
	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new NetworkHeaderFieldsRewriter(id);
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {
		// No config for 'NetworkHeaderFieldsRewriter'
	}
}
