package org.openboxprotocol.types;

import org.moonlightcontroller.exceptions.JSONParseException;

public class TransportPort implements ValueType<TransportPort> {

	private int port;
	
	private static final int PORT_NUM_ANY = 0xFFFFFFFF;
	
	public static final TransportPort EMPTY_MASK = new TransportPort(0);
	public static final TransportPort ANY = new TransportPort(PORT_NUM_ANY);
	
	public TransportPort(int port) {
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
	
	public static TransportPort of(int port) {
		return new TransportPort(port);
	}
}
