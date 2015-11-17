package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class IpEcn implements ValueType<IpEcn> {

	private int ipEcn;
	
	public static final IpEcn EMPTY_MASK = new IpEcn(0x0);

	private IpEcn(int ipEcn) {
		this.ipEcn = ipEcn;
	}
	
	public int getValue() {
		return ipEcn;
	}

	@Override
	public IpEcn applyMask(IpEcn mask) {
		return IpEcn.of(this.ipEcn & mask.ipEcn);
	}
	
	@Override
	public int hashCode() {
		return this.ipEcn * 5;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IpEcn) && ((IpEcn)other).ipEcn == this.ipEcn;
	}
	
	public static IpEcn of(int ipEcn) {
		return new IpEcn(ipEcn);
	}

	public static IpEcn fromJson(Object json) throws JSONParseException {
		if (!(json instanceof Integer))
			throw new JSONParseException("IP ECN must be an integer");
		return new IpEcn((Integer)json);
	}

}
