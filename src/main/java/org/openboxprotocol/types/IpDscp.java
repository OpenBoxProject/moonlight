package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class IpDscp implements ValueType<IpDscp> {

	private int ipDscp;
	
	public static final IpDscp EMPTY_MASK = new IpDscp(0x0);

	private IpDscp(int ipDscp) {
		this.ipDscp = ipDscp;
	}
	
	public int getValue() {
		return ipDscp;
	}
	
	@Override
	public IpDscp applyMask(IpDscp mask) {
		return IpDscp.of(this.ipDscp & mask.ipDscp);
	}
	
	@Override
	public int hashCode() {
		return this.ipDscp * 7;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IpDscp) && ((IpDscp)other).ipDscp == this.ipDscp;
	}
	
	public static IpDscp of(int ipDscp) {
		return new IpDscp(ipDscp);
	}

	public static IpDscp fromJson(Object json) throws JSONParseException {
		if (!(json instanceof Integer))
			throw new JSONParseException("IP DSCP must be an integer");
		return new IpDscp((Integer)json);
	}
}
