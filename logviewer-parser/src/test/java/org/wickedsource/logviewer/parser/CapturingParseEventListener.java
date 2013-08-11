package org.wickedsource.logviewer.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;

public class CapturingParseEventListener implements ParseEventListener {

	private static Logger logger = LoggerFactory.getLogger(CapturingParseEventListener.class);
	
	/**
	 * Map that stores all ParseEvents that occured during Parsing of a certain
	 * log line.
	 */
	Map<Integer, List<ParseEvent>> capturedEvents = new HashMap<Integer, List<ParseEvent>>();

	public void handleEvent(ParseEvent event) {
		List<ParseEvent> eventsForLine = capturedEvents.get(event.getLineNumber());
		if (eventsForLine == null) {
			eventsForLine = new ArrayList<ParseEvent>();
			capturedEvents.put(event.getLineNumber(), eventsForLine);
		}
		eventsForLine.add(event);
		logger.info(String.format("Captured event of type %s!", event.getClass()));
	}

	public List<ParseEvent> getEventsForLine(Long lineNumber) {
		List<ParseEvent> eventsForLine = capturedEvents.get(lineNumber);
		if (eventsForLine == null) {
			return new ArrayList<ParseEvent>();
		} else {
			return eventsForLine;
		}
	}
}
