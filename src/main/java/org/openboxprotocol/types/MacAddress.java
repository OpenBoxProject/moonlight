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
		return new MacAddress(this.mac & mask.mac);
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
	
	private String toStringValue = null;
	
	@Override
	public String toString() {
		if (toStringValue == null) {
			synchronized (this) {
				if (toStringValue == null) {
					StringBuilder sb = new StringBuilder();
					long val;
					long mac = this.mac;
					for (int i = 0; i < 6; i++) {
						val = mac & 0x0FF;
						sb.insert(0, Long.toHexString(val));
						if (i < 5)
							sb.insert(0, ':');
						mac >>= 8;
					}
					toStringValue = sb.toString();
				}
			}
		}
		return toStringValue;
	}
	
	public static void main(String[] args) {
		MacAddress m = MacAddress.of("11:22:33:44:55:66");
		String s = m.toString();
		System.out.println(s);
	}
	
	public static MacAddress fromJson(Object json) throws JSONParseException {
		if (json instanceof Long) {
			return new MacAddress((Long)json);
		} else if (json instanceof String) {
			return MacAddress.of((String)json);
		}
		return null;
	}
	

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.mac);
	}

	@Override
	public String toJson() {
		return this.toString();
	}

}
