package org.wickedsource.logviewer.parser.listener;

import org.wickedsource.logviewer.parser.event.ParseEvent;

public class NoopEventListener implements ParseEventListener {

	@Override
	public void handleEvent(ParseEvent event) {
		// no op
	}

}
