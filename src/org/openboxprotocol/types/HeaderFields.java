package org.openboxprotocol.types;

public enum HeaderFields {
	IN_PORT(0), /* Switch input port. */
	IN_PHY_PORT(1), /* Switch physical input port. */
	METADATA(2), /* Metadata passed between tables. */
	ETH_DST(3), /* Ethernet destination address. */
	ETH_SRC(4), /* Ethernet source address. */
	ETH_TYPE(5), /* Ethernet frame type. */
	VLAN_VID(6), /* VLAN id. */
	VLAN_PCP(7), /* VLAN priority. */
	IP_DSCP(8), /* IP DSCP (6 bits in ToS field). */
	IP_ECN(9), /* IP ECN (2 bits in ToS field). */
	IP_PROTO(10), /* IP protocol. */
	IPV4_SRC(11), /* IPv4 source address. */
	IPV4_DST(12), /* IPv4 destination address. */
	TCP_SRC(13), /* TCP source port. */
	TCP_DST(14), /* TCP destination port. */
	UDP_SRC(15), /* UDP source port. */
	UDP_DST(16), /* UDP destination port. */
	SCTP_SRC(17), /* SCTP source port. */
	SCTP_DST(18), /* SCTP destination port. */
	ICMPV4_TYPE(19), /* ICMP type. */
	ICMPV4_CODE(20), /* ICMP code. */
	ARP_OP(21), /* ARP opcode. */
	ARP_SPA(22), /* ARP source IPv4 address. */
	ARP_TPA(23), /* ARP target IPv4 address. */
	ARP_SHA(24), /* ARP source hardware address. */
	ARP_THA(25), /* ARP target hardware address. */
	IPV6_SRC(26), /* IPv6 source address. */
	IPV6_DST(27), /* IPv6 destination address. */
	IPV6_FLABEL(28), /* IPv6 Flow Label */
	ICMPV6_TYPE(29), /* ICMPv6 type. */
	ICMPV6_CODE(30), /* ICMPv6 code. */
	IPV6_ND_TARGET(31), /* Target address for ND. */
	IPV6_ND_SLL(32), /* Source link-layer for ND. */
	IPV6_ND_TLL(33), /* Target link-layer for ND. */
	MPLS_LABEL(34), /* MPLS label. */
	MPLS_TC(35), /* MPLS TC. */
	OFPXMT_OFP_MPLS_BOS(36), /* MPLS BoS bit. */
	PBB_ISID(37), /* PBB I-SID. */
	TUNNEL_ID(38), /* Logical Port Metadata. */
	IPV6_EXTHDR(39), /* IPv6 Extension Header pseudo-field */
	PBB_UCA(41) /* PBB UCA header field. */
	;
	
	public final int id;
	
	private HeaderFields(int id) {
		this.id = id;
	}
	
}
