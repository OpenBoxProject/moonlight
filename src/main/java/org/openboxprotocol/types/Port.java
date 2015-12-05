package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class Port implements ValueType<Port> {

	private static final int PORT_NUM_ANY = 0xFFFFFFFF;
	
	private int port;
	
	public static final Port EMPTY_MASK = new Port(0);
	public static final Port ANY = new Port(PORT_NUM_ANY);
	
	private Port(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}

	@Override
	public Port applyMask(Port mask) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return port * 11;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof Port) && ((Port)other).port == this.port;
	}
	
	public static Port fromJson(Object json) throws JSONParseException {
		if (json instanceof String) {
			if (((String)json).equals("any")) {
				return ANY;
			} else {
				throw new JSONParseException("Invalid port: " + json);
			}
		} else if (json instanceof Integer) {
			return new Port(((Integer)json).intValue());
		} else {
			throw new JSONParseException("Invalid port: " + json);
		}
	}
}
