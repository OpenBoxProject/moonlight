package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class MacAddress implements ValueType<MacAddress> {

	private long mac;
	
	public static final MacAddress EMPTY_MASK = new MacAddress(0);
	
	private MacAddress(long mac) {
		this.mac = mac;
	}
	
	@Override
	public MacAddress applyMask(MacAddress mask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		return (int)mac;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof MacAddress) && ((MacAddress)other).mac == this.mac;
	}
	
	public static MacAddress fromJson(Object json) throws JSONParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
