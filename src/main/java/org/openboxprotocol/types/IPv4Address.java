package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class IPv4Address implements ValueType<IPv4Address> {

	private long ip;
	
	public static final IPv4Address EMPTY_MASK = new IPv4Address(0x0);

	private IPv4Address(long ip) {
		this.ip = ip;
	}

	@Override
	public IPv4Address applyMask(IPv4Address mask) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return (int)this.ip;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IPv4Address) && ((IPv4Address)other).ip == this.ip;
	}
	
	public static IPv4Address fromJson(Object json) throws JSONParseException {
		if (!(json instanceof String)) {
			throw new JSONParseException("Invalid value for IPv4 address: " + json);
		}
		
		String addr = (String)json;
		String[] parts = addr.split("\\.");
		if (parts.length != 4)
			throw new JSONParseException("Invalid value for IPv4 address: " + addr);
		try {
			long ip = 0;
			for (int i = 0; i < 4; i++) {
				ip |= (Integer.parseInt(parts[i]) & 0x0FF);
				if (i < 3)
					ip <<= 8;
			}
			return new IPv4Address(ip);
		} catch (Exception e) {
			throw new JSONParseException("Invalid value for IPv4 address: " + addr);
		}
	}

}
