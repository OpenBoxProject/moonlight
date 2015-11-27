package org.openboxprotocol.types;

public enum HeaderFields {
	ETH_DST(0), /* Ethernet destination address. */
	ETH_SRC(1), /* Ethernet source address. */
	ETH_TYPE(2), /* Ethernet frame type. */
	VLAN_VID(3), /* VLAN id. */
	VLAN_PCP(4), /* VLAN priority. */
	IP_DSCP(5), /* IP DSCP (6 bits in ToS field). */
	IP_ECN(6), /* IP ECN (2 bits in ToS field). */
	IP_PROTO(7), /* IP protocol. */
	IPV4_SRC(8), /* IPv4 source address. */
	IPV4_DST(9), /* IPv4 destination address. */
	TCP_SRC(10), /* TCP source port. */
	TCP_DST(11), /* TCP destination port. */
	UDP_SRC(12), /* UDP source port. */
	UDP_DST(13), /* UDP destination port. */
	SCTP_SRC(14), /* SCTP source port. */
	SCTP_DST(15), /* SCTP destination port. */
	ICMPV4_TYPE(16), /* ICMP type. */
	ICMPV4_CODE(17), /* ICMP code. */
	ARP_OP(18), /* ARP opcode. */
	ARP_SPA(19), /* ARP source IPv4 address. */
	ARP_TPA(20), /* ARP target IPv4 address. */
	ARP_SHA(21), /* ARP source hardware address. */
	ARP_THA(22), /* ARP target hardware address. */
	IPV6_SRC(23), /* IPv6 source address. */
	IPV6_DST(24), /* IPv6 destination address. */
	IPV6_FLABEL(25), /* IPv6 Flow Label */
	ICMPV6_TYPE(26), /* ICMPv6 type. */
	ICMPV6_CODE(27), /* ICMPv6 code. */
	IPV6_ND_TARGET(28), /* Target address for ND. */
	IPV6_ND_SLL(29), /* Source link-layer for ND. */
	IPV6_ND_TLL(30), /* Target link-layer for ND. */
	MPLS_LABEL(31), /* MPLS label. */
	MPLS_TC(32), /* MPLS TC. */
	OFPXMT_OFP_MPLS_BOS(33), /* MPLS BoS bit. */
	PBB_ISID(34), /* PBB I-SID. */
	TUNNEL_ID(35), /* Logical Port Metadata. */
	IPV6_EXTHDR(36), /* IPv6 Extension Header pseudo-field */
	PBB_UCA(37) /* PBB UCA header field. */
	;
	
	public final int id;
	
	private HeaderFields(int id) {
		this.id = id;
	}
	
}
