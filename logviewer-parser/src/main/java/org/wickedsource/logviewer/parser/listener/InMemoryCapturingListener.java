package org.wickedsource.logviewer.parser.listener;

import java.util.ArrayList;
import java.util.List;

import org.wickedsource.logviewer.parser.event.LineSuccessfullyParsedEvent;
import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.model.LogEntry;

/**
 * A simple event listener that stores the {@link LogEntry}s of successfully
 * parsed lines in a list and thus holds them in memory.
 * <strong>Beware:</strong> the whole log file will be stored in memory!
 * <p/>
 * The parsed {@link LogEntry}s can be accessed by calling
 * {@link #getLogEntries()}.
 * 
 * @author Tom
 * 
 */
public class InMemoryCapturingListener implements ParseEventListener {

	private List<LogEntry> logEntries = new ArrayList<LogEntry>();

	@Override
	public void handleEvent(ParseEvent event) {
		if (event instanceof LineSuccessfullyParsedEvent) {
			logEntries.add(((LineSuccessfullyParsedEvent) event).getLogEntry());
		}
	}

	/**
	 * Returns the {@link LogEntry}s of all successfully parsed lines.
	 * 
	 * @return the {@link LogEntry}s of all successfully parsed lines.
	 */
	public List<LogEntry> getLogEntries() {
		return this.logEntries;
	}

}
