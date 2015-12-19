package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.types.IPv4Address;
import org.openboxprotocol.types.MacAddress;

import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class NetworkHeaderFieldsRewriter extends ProcessingBlock {
	private MacAddress eth_src;
	private MacAddress eth_dst;
	private int eth_type;
	private int ipv4_proto;
	private int ipv4_dscp;
	private int ipv4_ecn;
	private int ipv4_ttl;
	private IPv4Address ipv4_src;
	private IPv4Address ipv4_dst;
	private int tcp_src;
	private int tcp_dst;
	private int udp_src;
	private int udp_dst;

	public NetworkHeaderFieldsRewriter(String id, MacAddress eth_src, MacAddress eth_dst, int eth_type, int ipv4_proto, int ipv4_dscp, int ipv4_ecn, int ipv4_ttl, IPv4Address ipv4_src, IPv4Address ipv4_dst, int tcp_src, int tcp_dst, int udp_src, int udp_dst) {
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
	}

	public MacAddress getEth_src() {
		return eth_src;
	}

	public MacAddress getEth_dst() {
		return eth_dst;
	}

	public int getEth_type() {
		return eth_type;
	}

	public int getIpv4_proto() {
		return ipv4_proto;
	}

	public int getIpv4_dscp() {
		return ipv4_dscp;
	}

	public int getIpv4_ecn() {
		return ipv4_ecn;
	}

	public int getIpv4_ttl() {
		return ipv4_ttl;
	}

	public IPv4Address getIpv4_src() {
		return ipv4_src;
	}

	public IPv4Address getIpv4_dst() {
		return ipv4_dst;
	}

	public int getTcp_src() {
		return tcp_src;
	}

	public int getTcp_dst() {
		return tcp_dst;
	}

	public int getUdp_src() {
		return udp_src;
	}

	public int getUdp_dst() {
		return udp_dst;
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
		config.put("eth_src", this.eth_src.toString());
		config.put("eth_dst", this.eth_dst.toString());
		config.put("eth_type", this.eth_type+ "");
		config.put("ipv4_proto", this.ipv4_proto+ "");
		config.put("ipv4_dscp", this.ipv4_dscp+ "");
		config.put("ipv4_ecn", this.ipv4_ecn+ "");
		config.put("ipv4_ttl", this.ipv4_ttl+ "");
		config.put("ipv4_src", this.ipv4_src.toString());
		config.put("ipv4_dst", this.ipv4_dst.toString());
		config.put("tcp_src", this.tcp_src+ "");
		config.put("tcp_dst", this.tcp_dst+ "");
		config.put("udp_src", this.udp_src+ "");
		config.put("udp_dst", this.udp_dst+ "");
	}
}
