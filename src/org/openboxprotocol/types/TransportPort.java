package org.openboxprotocol.types;

import org.openboxprotocol.protocol.parsing.JSONParseException;

public class TransportPort implements ValueType<TransportPort> {

	private int port;
	
	public static final TransportPort EMPTY_MASK = new TransportPort(0);
	
	private TransportPort(int port) {
		this.port = port;
	}

	@Override
	public TransportPort applyMask(TransportPort mask) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TransportPort fromJson(Object json) throws JSONParseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return this.port * 61;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof TransportPort) && ((TransportPort)other).port == this.port;
	}
}
