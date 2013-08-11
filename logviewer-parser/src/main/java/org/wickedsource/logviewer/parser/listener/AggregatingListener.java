package org.wickedsource.logviewer.parser.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wickedsource.logviewer.parser.event.LineSuccessfullyParsedEvent;
import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.model.AggregatedLogEntry;
import org.wickedsource.logviewer.parser.model.LogEntry;

public class AggregatingListener implements ParseEventListener {

	/**
	 * This map stores all {@link LogEntry}s that contain a stacktrace.
	 */
	private Map<Integer, AggregatedLogEntry> entriesByStacktraceHashCode = new HashMap<Integer, AggregatedLogEntry>();

	/**
	 * This map stores all {@link LogEntry}s that do NOT contain a stacktrace.
	 */
	private Map<String, Map<String, Map<String, AggregatedLogEntry>>> entriesBySeverityLoggerAndMessage = new HashMap<String, Map<String, Map<String, AggregatedLogEntry>>>();

	@Override
	public void handleEvent(ParseEvent event) {
		if (event instanceof LineSuccessfullyParsedEvent) {

			LogEntry entry = ((LineSuccessfullyParsedEvent) event).getLogEntry();

//			if (!entry.getStackTrace().isEmpty()) {
//
//				// when the stacktrace is not empty and the hashcode of the
//				// stacktrace is equal, we assume that it's the same message
//
//				AggregatedLogEntry similarEntry = entriesByStacktraceHashCode.get(entry.getStackTrace().hashCode());
//				if (similarEntry != null) {
//					similarEntry.setAggregateCount(similarEntry.getAggregateCount() + 1);
//				} else {
//					entriesByStacktraceHashCode.put(entry.getStackTrace().hashCode(), new AggregatedLogEntry(entry));
//				}
//
//			} else {

				// finding entries with the same severity
				Map<String, Map<String, AggregatedLogEntry>> entriesByLoggerAndMessage = entriesBySeverityLoggerAndMessage
						.get(entry.getSeverity());
				if (entriesByLoggerAndMessage == null) {
					entriesByLoggerAndMessage = new HashMap<String, Map<String, AggregatedLogEntry>>();
					entriesBySeverityLoggerAndMessage.put(entry.getSeverity(), entriesByLoggerAndMessage);
				}

				// finding entries with the same severity and logger
				Map<String, AggregatedLogEntry> entriesByMessage = entriesByLoggerAndMessage.get(entry.getLogger());
				if (entriesByMessage == null) {
					entriesByMessage = new HashMap<String, AggregatedLogEntry>();
					entriesByLoggerAndMessage.put(entry.getLogger(), entriesByMessage);
				}

				// finding entries with the same severity, logger and message
				AggregatedLogEntry similarEntry = entriesByMessage.get(entry.getMessage());
				if (similarEntry == null) {
					entriesByMessage.put(entry.getMessage(), new AggregatedLogEntry(entry));
				} else {
					similarEntry.setAggregateCount(similarEntry.getAggregateCount() + 1);
				}

//			}

		}
	}

	/**
	 * Returns the {@link AggregatedLogEntry}s of all successfully parsed lines.
	 * The entries are sorted by aggregate count.
	 * 
	 * @return the {@link AggregatedLogEntry}s of all successfully parsed lines.
	 */
	public List<AggregatedLogEntry> getLogEntries() {

		List<AggregatedLogEntry> entries = new ArrayList<AggregatedLogEntry>();
		entries.addAll(entriesByStacktraceHashCode.values());

		for (Map<String, Map<String, AggregatedLogEntry>> entriesByLoggerAndMessage : entriesBySeverityLoggerAndMessage
				.values()) {
			for (Map<String, AggregatedLogEntry> entriesByMessage : entriesByLoggerAndMessage.values()) {
				entries.addAll(entriesByMessage.values());
			}
		}

		Collections.sort(entries, AggregatedLogEntry.comparator());
		return entries;
	}

}
