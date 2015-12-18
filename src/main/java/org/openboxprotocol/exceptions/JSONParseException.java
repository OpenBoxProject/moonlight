package org.openboxprotocol.exceptions;

import org.moonlightcontroller.exceptions.ParseException;

public class JSONParseException extends ParseException {

	private static final long serialVersionUID = 1L;

	public JSONParseException(String msg) {
		super(msg);
	}
}
