package org.wickedsource.logviewer.parser;

import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent;
import org.wickedsource.logviewer.parser.event.LineSuccessfullyParsedEvent;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent.Reason;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;
import org.wickedsource.logviewer.parser.model.LogEntry;

/**
 * This strategy parses a "normal" log line whose message spans multiple
 * following lines.
 * 
 * @author Tom
 * 
 */
public class MultiLineParseStrategy implements ParseStrategy {

	private SimpleParseStrategy simpleStrategy;

	public MultiLineParseStrategy(LineDescriptor descriptor) {
		this.simpleStrategy = new SimpleParseStrategy(descriptor);
	}

	@Override
	public void parseLine(ParseWindow window, ParseEventListener listener) {
		LogEntry logEntry = parseBaseLine(window, listener);

		if (logEntry == null) {
			listener.handleEvent(new LineNotParsedEvent(window.getFocusLineNumber(), Reason.AMBIGUOUS_PATTERN));
			return;
		}

		// All lines after the main line that do not match the simple pattern
		// are considered part of the multi-line message of the main line.
		String nextLine = window.getFutureLine(1);
		while (nextLine != null && !simpleStrategy.matches(nextLine)) {
			logEntry.setMessage(logEntry.getMessage() + "\n" + nextLine);
			window.slideForward();
			nextLine = window.getFutureLine(1);
		}

		listener.handleEvent(new LineSuccessfullyParsedEvent(logEntry.getLineNumber(), logEntry));

	}

	private LogEntry parseBaseLine(ParseWindow window, ParseEventListener listener) {
		return simpleStrategy.parseLineSynchronous(window, listener);
	}

	@Override
	public boolean hasSolutionFor(ParseWindow window) {
		String currentLine = window.getFocusLine();
		String nextLine = window.getFutureLine(1);
		if (nextLine == null) {
			return false;
		} else {
			return simpleStrategy.matches(currentLine) && !simpleStrategy.matches(nextLine);
		}
	}

}
