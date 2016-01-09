package org.openboxprotocol.protocol;

import org.openboxprotocol.types.EthType;
import org.openboxprotocol.types.HeaderFields;
import org.openboxprotocol.types.IPv4Address;
import org.openboxprotocol.types.IPv6Address;
import org.openboxprotocol.types.IpDscp;
import org.openboxprotocol.types.IpEcn;
import org.openboxprotocol.types.IpProto;
import org.openboxprotocol.types.MacAddress;
import org.openboxprotocol.types.TransportPort;
import org.openboxprotocol.types.ValueType;
import org.openboxprotocol.types.VlanPcp;
import org.openboxprotocol.types.VlanVid;

public class HeaderField<F extends ValueType<F>> implements Comparable<HeaderField<?>> {
	
	private final String name;
	public final HeaderFields id;
	public final F noMask;
	
	private final Prerequisite<?>[] prerequisites;
	
	private HeaderField(final String name, final HeaderFields id, F noMask, Prerequisite<?>... prerequisites) {
		this.name = name;
		this.id = id;
		this.noMask = noMask;
		this.prerequisites = prerequisites;
	}
	
	public final static HeaderField<MacAddress> ETH_DST =
			new HeaderField<MacAddress>("ETH_DST", HeaderFields.ETH_DST, MacAddress.EMPTY_MASK);
	
	public final static HeaderField<MacAddress> ETH_SRC =
			new HeaderField<MacAddress>("ETH_SRC", HeaderFields.ETH_SRC, MacAddress.EMPTY_MASK);
	
	public final static HeaderField<EthType> ETH_TYPE =
			new HeaderField<EthType>("ETH_TYPE", HeaderFields.ETH_TYPE, EthType.EMPTY_MASK);
	
	public final static HeaderField<VlanVid> VLAN_VID =
			new HeaderField<VlanVid>("VLAN_VID", HeaderFields.VLAN_VID, VlanVid.EMPTY_MASK);
	
	public final static HeaderField<VlanPcp> VLAN_PCP =
			new HeaderField<VlanPcp>("VLAN_PCP", HeaderFields.VLAN_PCP, VlanPcp.EMPTY_MASK,
					new Prerequisite<VlanVid>(HeaderField.VLAN_VID));
	
	public final static HeaderField<IpDscp> IP_DSCP =
			new HeaderField<IpDscp>("IP_DSCP", HeaderFields.IP_DSCP, IpDscp.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv4, EthType.IPv6));
	
	public final static HeaderField<IpEcn> IP_ECN =
			new HeaderField<IpEcn>("IP_ECN", HeaderFields.IP_ECN, IpEcn.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv4, EthType.IPv6));
	
	public final static HeaderField<IpProto> IP_PROTO =
			new HeaderField<IpProto>("IP_PROTO", HeaderFields.IP_PROTO, IpProto.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv4, EthType.IPv6));
	
	public final static HeaderField<IPv4Address> IPV4_SRC =
			new HeaderField<IPv4Address>("IPV4_SRC", HeaderFields.IPV4_SRC, IPv4Address.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv4));
	
	public final static HeaderField<IPv4Address> IPV4_DST =
			new HeaderField<IPv4Address>("IPV4_DST", HeaderFields.IPV4_DST, IPv4Address.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv4));
	
	public final static HeaderField<TransportPort> TCP_SRC = new HeaderField<TransportPort>(
			"TCP_SRC", HeaderFields.TCP_SRC, TransportPort.EMPTY_MASK,
			new Prerequisite<IpProto>(HeaderField.IP_PROTO, IpProto.TCP));
	
	public final static HeaderField<TransportPort> TCP_DST = new HeaderField<TransportPort>(
			"TCP_DST", HeaderFields.TCP_DST, TransportPort.EMPTY_MASK,
			new Prerequisite<IpProto>(HeaderField.IP_PROTO, IpProto.TCP));
	
	public final static HeaderField<TransportPort> UDP_SRC = new HeaderField<TransportPort>(
			"UDP_SRC", HeaderFields.UDP_SRC, TransportPort.EMPTY_MASK,
			new Prerequisite<IpProto>(HeaderField.IP_PROTO, IpProto.UDP));
	
	public final static HeaderField<TransportPort> UDP_DST = new HeaderField<TransportPort>(
			"UDP_DST", HeaderFields.UDP_DST, TransportPort.EMPTY_MASK,
			new Prerequisite<IpProto>(HeaderField.IP_PROTO, IpProto.UDP));
	
	public final static HeaderField<IPv6Address> IPV6_SRC =
			new HeaderField<IPv6Address>("IPV6_SRC", HeaderFields.IPV6_SRC, IPv6Address.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv6));
	
	public final static HeaderField<IPv6Address> IPV6_DST =
			new HeaderField<IPv6Address>("IPV6_DST", HeaderFields.IPV6_DST, IPv6Address.EMPTY_MASK,
					new Prerequisite<EthType>(HeaderField.ETH_TYPE, EthType.IPv6));
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean arePrerequisitesOK(HeaderMatch match) {
		for (Prerequisite<?> p : this.prerequisites) {
			if (!p.isSatisfied(match)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(HeaderField<?> o) {
		return this.id.ordinal() - o.id.ordinal();
	}
}
