package org.openboxprotocol.protocol.parsing;

import java.io.IOException;

public class JSONParseException extends IOException {

	private static final long serialVersionUID = 1L;

	public JSONParseException(String msg) {
		super(msg);
	}
}
