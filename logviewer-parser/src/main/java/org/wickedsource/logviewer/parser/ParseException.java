package org.wickedsource.logviewer.parser;

/**
 * This exception is thrown if the parsing has stopped due to some error.
 * 
 * @author Tom
 * 
 */
public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

}
