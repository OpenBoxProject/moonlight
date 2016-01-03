package org.openboxprotocol.types;

import java.io.IOException;

import org.openboxprotocol.exceptions.JSONParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MacAddress extends AbstractValueType<MacAddress> {

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
	
	public static MacAddress of(String mac) {
		String[] parts = mac.split(":");
		if (parts.length != 6)
			throw new IllegalArgumentException("Illegal MAC address: " + mac);
		long value = 0;
		for (int i = 0; i < 6; i++) {
			value <<= 8;
			value |= 0x0FF & Integer.parseInt(parts[i], 16);
		}
		return new MacAddress(value);
	}
	
	public static MacAddress fromJson(Object json) throws JSONParseException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.mac);
	}


}
