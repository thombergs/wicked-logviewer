package org.wickedsource.logviewer.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.logviewer.parser.event.LineSuccessfullyParsedEvent;
import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;

public class ProgressPrintingParseEventListener implements ParseEventListener {

	Logger logger = LoggerFactory.getLogger(ProgressPrintingParseEventListener.class);

	private int counter = 0;

	private int printInterval;

	private int previousModulo;

	public ProgressPrintingParseEventListener(int printInterval) {
		this.printInterval = printInterval;
		this.previousModulo = printInterval;
	}

	@Override
	public void handleEvent(ParseEvent event) {
		if (event instanceof LineSuccessfullyParsedEvent) {
			counter = event.getLineNumber();
			int modulo = counter & printInterval;
			if (modulo < previousModulo) {
				System.out.println(String.format("parsed %s lines"));
				previousModulo = modulo;
			}
		}
	}

}
