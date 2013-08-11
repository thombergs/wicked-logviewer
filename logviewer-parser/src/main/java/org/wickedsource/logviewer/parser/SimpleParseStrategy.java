package org.wickedsource.logviewer.parser;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wickedsource.logviewer.parser.descriptor.CustomFieldDescriptor;
import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent.Reason;
import org.wickedsource.logviewer.parser.event.LineSuccessfullyParsedEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;
import org.wickedsource.logviewer.parser.model.LogEntry;

/**
 * This strategy parses a "normal" line of a log file, i.e. a line that fits the
 * pattern as described by a {@link LineDescriptor}, without caring about the
 * context of the log line.
 * 
 * @author Tom
 */
public class SimpleParseStrategy implements ParseStrategy {

	private final LineDescriptor descriptor;

	private Pattern pattern;

	public SimpleParseStrategy(LineDescriptor descriptor) {
		this.descriptor = descriptor;
		pattern = Pattern.compile(descriptor.getLineRegex());
	}

	public void parseLine(ParseWindow window, ParseEventListener listener) {
		LogEntry logEntry = parseLineSynchronous(window, listener);
		if (logEntry != null) {
			listener.handleEvent(new LineSuccessfullyParsedEvent(window.getFocusLineNumber(), logEntry));
		}
	}

	public LineDescriptor getDescriptor() {
		return descriptor;
	}

	protected LogEntry parseLineSynchronous(ParseWindow window, ParseEventListener listener) {
		Matcher matcher = pattern.matcher(window.getFocusLine());

		int lineNumber = window.getFocusLineNumber();

		if (!matcher.matches()) {
			listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.LINE_REGEX_DOES_NOT_MATCH));
			return null;
		}

		LogEntry entry = new LogEntry();
		entry.setLineNumber(lineNumber);

		Date timestamp = parseTimestampField(matcher, descriptor);
		if (timestamp == null) {
			listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.DATE_PATTERN_DOES_NOT_MATCH));
			return null;
		} else {
			entry.setTimestamp(timestamp);
		}

		String severity = parseSeverityField(matcher, descriptor);
		if (severity == null) {
			listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.SEVERITY_FIELD_MISSING));
			return null;
		} else {
			entry.setSeverity(severity);
		}

		String logger = parseLoggerField(matcher, descriptor);
		if (logger == null) {
			listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.LOGGER_FIELD_MISSING));
			return null;
		} else {
			entry.setLogger(logger);
		}

		String message = parseMessageField(matcher, descriptor);
		if (message == null) {
			listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.MESSAGE_FIELD_MISSING));
			return null;
		} else {
			entry.setMessage(message);
		}

		for (CustomFieldDescriptor fieldDescriptor : descriptor.getCustomFieldDescriptors()) {
			String field = parseCustomField(matcher, fieldDescriptor);
			if (field == null) {
				listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.CUSTOM_FIELD_MISSING));
				return null;
			} else {
				entry.setCustomField(fieldDescriptor.getKey(), field);
			}
		}

		return entry;
	}

	private String parseLoggerField(Matcher matcher, LineDescriptor descriptor2) {
		String logger = matcher.group(descriptor.getLoggerGroup());
		return logger;
	}

	private Date parseTimestampField(Matcher matcher, LineDescriptor descriptor) {
		try {
			String dateString = matcher.group(descriptor.getDateGroup());
			Date date = descriptor.getDateFormat().parse(dateString);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

	private String parseSeverityField(Matcher matcher, LineDescriptor descriptor) {
		String severity = matcher.group(descriptor.getSeverityGroup());
		return severity;
	}

	private String parseMessageField(Matcher matcher, LineDescriptor descriptor) {
		String message = matcher.group(descriptor.getMessageGroup());
		return message;
	}

	private String parseCustomField(Matcher matcher, CustomFieldDescriptor fieldDescriptor) {
		String field = matcher.group(fieldDescriptor.getFieldGroup());
		return field;
	}

	@Override
	public boolean hasSolutionFor(ParseWindow window) {
		return matches(window.getFocusLine());
	}

	protected boolean matches(String line) {
		Matcher matcher = pattern.matcher(line);
		return matcher.matches();
	}

}
