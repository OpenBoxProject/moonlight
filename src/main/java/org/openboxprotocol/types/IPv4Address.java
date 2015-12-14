package org.openboxprotocol.types;

import org.moonlightcontroller.exceptions.JSONParseException;
import org.moonlightcontroller.exceptions.ParseException;

public class IPv4Address implements ValueType<IPv4Address> {

	private long ip;
	
	public static final IPv4Address EMPTY_MASK = new IPv4Address(0x0);

	private IPv4Address(long ip) {
		this.ip = ip;
	}

	@Override
	public IPv4Address applyMask(IPv4Address mask) {
		return new IPv4Address(this.ip & mask.ip);
	}
	
	@Override
	public int hashCode() {
		return (int)this.ip;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IPv4Address) && ((IPv4Address)other).ip == this.ip;
	}
	
	private String toStringValue = null;
	
	@Override
	public String toString() {
		if (toStringValue == null) {
			synchronized (this) {
				if (toStringValue == null) {
					long v = this.ip;
					String[] parts = new String[4];
					for (int i = 0; i < 4; i++) {
						parts[3 - i] = Long.toString(v & 0x0FF);
						v >>= 8;
					}
					toStringValue = String.join(".", parts);
				}
			}
		}
		return toStringValue;
	}
	
	private static IPv4Address fromString(String addr) throws ParseException {
		String[] parts = addr.split("\\.");
		if (parts.length != 4)
			throw new ParseException("Invalid value for IPv4 address: " + addr);
		try {
			long ip = 0;
			for (int i = 0; i < 4; i++) {
				ip |= (Integer.parseInt(parts[i]) & 0x0FF);
				if (i < 3)
					ip <<= 8;
			}
			return new IPv4Address(ip);
		} catch (Exception e) {
			throw new ParseException("Invalid value for IPv4 address: " + addr);
		}
	}
	
	public static IPv4Address fromJson(Object json) throws JSONParseException {
		if (!(json instanceof String)) {
			throw new JSONParseException("Invalid value for IPv4 address: " + json);
		}
		
		String addr = (String)json;
		try {
			return fromString(addr);
		} catch (ParseException e) {
			throw new JSONParseException(e.getMessage());
		}
	}
	
	public static IPv4Address of(String addr) throws ParseException {
		return fromString(addr);
	}

}
