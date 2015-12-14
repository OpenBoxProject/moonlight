package org.openboxprotocol.types;

import org.moonlightcontroller.exceptions.JSONParseException;

public class IpProto implements ValueType<IpProto> {

	private int ipProto;
	
	private static final int IPP_ICMP = 1;
	private static final int IPP_TCP = 6;
	private static final int IPP_UDP = 17;
	
	private static final String IPP_ICMP_STR = "icmp";
	private static final String IPP_TCP_STR = "tcp";
	private static final String IPP_UDP_STR = "udp";
	
	public static final IpProto ICMP = new IpProto(IPP_ICMP);
	public static final IpProto TCP = new IpProto(IPP_TCP);
	public static final IpProto UDP = new IpProto(IPP_UDP);
	
	public static final IpProto EMPTY_MASK = new IpProto(0x0);

	private IpProto(int ipProto) {
		this.ipProto = ipProto;
	}
	
	public int getIpProto() {
		return ipProto;
	}

	@Override
	public IpProto applyMask(IpProto mask) {
		return IpProto.of(this.ipProto & mask.ipProto);
	}
	
	@Override
	public int hashCode() {
		return this.ipProto * 17;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IpProto) && ((IpProto)other).ipProto == this.ipProto;
	}
	
	@Override
	public String toString() {
		switch (ipProto) {
		case IPP_ICMP:
			return IPP_ICMP_STR;
		case IPP_TCP:
			return IPP_TCP_STR;
		case IPP_UDP:
			return IPP_UDP_STR;
		default:
			return Integer.toString(ipProto);
		}
	}
	
	public static IpProto of(int ipProto) {
		switch (ipProto) {
		case 0:
			return EMPTY_MASK;
		case IPP_ICMP:
			return ICMP;
		case IPP_TCP:
			return TCP;
		case IPP_UDP:
			return UDP;
		default:
			return new IpProto(ipProto);
		}
	}
	
	public static IpProto fromJson(Object json) throws JSONParseException {
		if (json instanceof Integer) {
			return IpProto.of(((Integer)json).intValue());
		} else if (json instanceof String) {
			if (((String)json).equalsIgnoreCase("icmp"))
				return ICMP;
			else if (((String)json).equalsIgnoreCase("tcp"))
				return TCP;
			else if (((String)json).equalsIgnoreCase("udp"))
				return UDP;
			else
				throw new JSONParseException("Unknown IP protocol: " + (String)json);
		} else {
			throw new JSONParseException("Invalid value for IP protocol: " + json);
		}
	}

}
