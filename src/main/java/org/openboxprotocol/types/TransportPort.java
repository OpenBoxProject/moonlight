package org.openboxprotocol.types;

import java.io.IOException;

import org.openboxprotocol.exceptions.JSONParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TransportPort extends AbstractValueType<TransportPort> {

	private int port;
	
	private static final int PORT_NUM_ANY = 0xFFFFFFFF;
	
	public static final TransportPort EMPTY_MASK = new TransportPort(0);
	public static final TransportPort ANY = new TransportPort(PORT_NUM_ANY);
	
	public TransportPort(int port) {
		this.port = port;
	}

	@Override
	public TransportPort applyMask(TransportPort mask) {
		return new TransportPort(this.port & mask.port);
	}

	public static TransportPort fromJson(Object json) throws JSONParseException {
		if (json instanceof Long) {
			return TransportPort.of(((Long)json).intValue());
		} else if (json instanceof Integer) {
				return TransportPort.of((Integer)json);
		} else if (json instanceof String) {
			String s = (String)json;
			if (s.equals("http"))
				return TransportPort.of(80);
			else
				return null;
		} else {
			return null;
		}
	}
	
	@Override
	public int hashCode() {
		return this.port * 61;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof TransportPort) && ((TransportPort)other).port == this.port;
	}
	
	@Override
	public String toString() {
		return Integer.toString(port);
	}
	
	public static TransportPort of(int port) {
		return new TransportPort(port);
	}

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.port);
	}
}
