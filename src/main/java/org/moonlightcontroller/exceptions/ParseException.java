package org.moonlightcontroller.exceptions;

import java.io.IOException;

/**
 * Exception thrown in case of parsing error
 *
 */
public class ParseException extends IOException {

	private static final long serialVersionUID = 1L;

	public ParseException(String msg) {
		super(msg);
	}
}
