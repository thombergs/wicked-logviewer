package org.wickedsource.logviewer.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent;
import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;

public class FailureLoggingListener implements ParseEventListener {

	private static Logger logger = LoggerFactory.getLogger(FailureLoggingListener.class);

	@Override
	public void handleEvent(ParseEvent event) {
		if (event instanceof LineNotParsedEvent) {
			logger.warn(String.format("Line %s could not be parsed due to %s", event.getLineNumber(),
					((LineNotParsedEvent) event).getReason()));
		}

	}

}
