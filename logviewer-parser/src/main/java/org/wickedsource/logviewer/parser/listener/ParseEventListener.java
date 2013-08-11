package org.wickedsource.logviewer.parser.listener;

import org.wickedsource.logviewer.parser.event.ParseEvent;

public interface ParseEventListener {
	
	void handleEvent(ParseEvent event);

}
