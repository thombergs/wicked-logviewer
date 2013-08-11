package org.wickedsource.logviewer.parser.event;

/**
 * This event is fired when a parser could not parse a line.
 */
public class LineNotParsedEvent extends ParseEvent {

	public enum Reason {
		LINE_REGEX_DOES_NOT_MATCH,

		DATE_PATTERN_DOES_NOT_MATCH,

		SEVERITY_FIELD_MISSING,

		MESSAGE_FIELD_MISSING,

		CUSTOM_FIELD_MISSING,

		AMBIGUOUS_PATTERN,

		NO_MATCHING_PARSE_STRATEGY,

		LOGGER_FIELD_MISSING;
	}

	private final Reason reason;

	public LineNotParsedEvent(int lineNumber, Reason reason) {
		super(lineNumber);
		this.reason = reason;
	}

	public Reason getReason() {
		return reason;
	}

}
