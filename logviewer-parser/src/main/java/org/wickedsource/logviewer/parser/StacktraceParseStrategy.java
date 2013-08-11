package org.wickedsource.logviewer.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent.Reason;
import org.wickedsource.logviewer.parser.event.LineSuccessfullyParsedEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;
import org.wickedsource.logviewer.parser.model.LogEntry;

/**
 * This strategy parses a log entry that contains a Java stacktrace. It looks
 * ahead and determines which lines still belong to the stacktrace and adds them
 * to the resulting {@link LogEntry} object.
 * <p/>
 * This strategy is able to parse a line that can be described with a
 * {@link LineDescriptor} and is followed by a Java stacktrace.
 * 
 * @author Tom
 */
public class StacktraceParseStrategy implements ParseStrategy {

	private static final Pattern STACKTRACE_PATTERN = Pattern
			.compile("at (([^\\(.]+\\.)+)([^\\(.]+)\\(([^:]+)(:([0-9]+))?\\)$");

	private SimpleParseStrategy simpleStrategy;

	public StacktraceParseStrategy(LineDescriptor descriptor) {
		this.simpleStrategy = new SimpleParseStrategy(descriptor);
	}

	public void parseLine(ParseWindow window, ParseEventListener listener) {

		LogEntry logEntry = parseBaseLine(window, listener);

		if (logEntry == null) {
			listener.handleEvent(new LineNotParsedEvent(window.getFocusLineNumber(), Reason.AMBIGUOUS_PATTERN));
			return;
		}

		// All lines after the main line that do not match the simple pattern or
		// the stacktrace pattern are considered part of the multi-line message
		// of the main line.
		String nextLine = window.getFutureLine(1);
		while (nextLine != null && !simpleStrategy.matches(nextLine) && !STACKTRACE_PATTERN.matcher(nextLine).matches()) {
			logEntry.setMessage(logEntry.getMessage() + "\n" + nextLine);
			window.slideForward();
			nextLine = window.getFutureLine(1);
		}

		// all lines after this until the next simple pattern belong to the
		// stacktrace
		String stacktraceLine = window.slideForward();
		Matcher matcher = STACKTRACE_PATTERN.matcher(stacktraceLine);
		while (matcher.matches()) {
			StackTraceElement element = parseStacktraceLine(window.getFocusLine(), window.getFocusLineNumber(),
					listener);
			logEntry.getStackTrace().add(element);
			nextLine = window.getFutureLine(1);
			if (STACKTRACE_PATTERN.matcher(nextLine).matches()) {
				window.slideForward();
				matcher = STACKTRACE_PATTERN.matcher(window.getFocusLine());
			} else {
				break;
			}
		}

		listener.handleEvent(new LineSuccessfullyParsedEvent(window.getFocusLineNumber(), logEntry));
	}

	private LogEntry parseBaseLine(ParseWindow window, ParseEventListener listener) {
		return simpleStrategy.parseLineSynchronous(window, listener);
	}

	@Override
	public boolean hasSolutionFor(ParseWindow window) {
		// the currently focused line has a stacktrace if the line after the
		// next one has the stacktrace pattern.
		String currentLine = window.getFocusLine();
		String lineAfterNext = window.getFutureLine(2);
		if (lineAfterNext == null) {
			return false;
		} else {
			return simpleStrategy.matches(currentLine) && STACKTRACE_PATTERN.matcher(lineAfterNext).matches();
		}
	}

	protected StackTraceElement parseStacktraceLine(String line, int lineNumber, ParseEventListener listener) {
		Matcher matcher = STACKTRACE_PATTERN.matcher(line);
		if (matcher.matches()) {
			String declaringClass = matcher.group(1);
			// cut the "." at the end
			declaringClass = declaringClass.substring(0, declaringClass.length() - 1);
			String methodName = matcher.group(3);
			String fileName = matcher.group(4);
			String lineNumberString = matcher.group(6);
			int traceLineNumber = 0;
			if (lineNumberString != null) {
				traceLineNumber = Integer.valueOf(lineNumberString);
			}
			StackTraceElement element = new StackTraceElement(declaringClass, methodName, fileName, traceLineNumber);
			return element;
		} else {
			listener.handleEvent(new LineNotParsedEvent(lineNumber, Reason.LINE_REGEX_DOES_NOT_MATCH));
			return null;
		}
	}

}
