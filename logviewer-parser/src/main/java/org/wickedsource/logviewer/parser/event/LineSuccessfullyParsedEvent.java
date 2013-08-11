package org.wickedsource.logviewer.parser.event;

import org.wickedsource.logviewer.parser.model.LogEntry;

/**
 * This event is fired when a parser successfully parsed a line. It contains the
 * parsed data as a {@link LogEntry} object.
 * 
 * @author Tom
 * 
 */
public class LineSuccessfullyParsedEvent extends ParseEvent {

	private final LogEntry logEntry;

	/**
	 * Constructor.
	 * 
	 * @param lineNumber
	 *            the number of the line that was parsed.
	 * @param logEntry
	 *            the data that resulted from parsing the line.
	 */
	public LineSuccessfullyParsedEvent(int lineNumber, LogEntry logEntry) {
		super(lineNumber);
		this.logEntry = logEntry;
	}

	/**
	 * Gets the data parsed from the line of the log.
	 * 
	 * @return the parsed data.
	 */
	public LogEntry getLogEntry() {
		return logEntry;
	}

}
