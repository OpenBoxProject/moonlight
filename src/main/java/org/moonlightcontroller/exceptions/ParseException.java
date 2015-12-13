package org.moonlightcontroller.exceptions;

import java.io.IOException;

public class ParseException extends IOException {

	private static final long serialVersionUID = 1L;

	public ParseException(String msg) {
		super(msg);
	}
}
