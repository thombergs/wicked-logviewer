package org.wickedsource.logviewer.parser.listener;

import java.util.ArrayList;
import java.util.List;

import org.wickedsource.logviewer.parser.event.ParseEvent;

/**
 * A dispatcher that acts as a {@link ParseEventListener} but does not actually
 * process events but simply forwards them to a list of registered listeners.
 * 
 * @author Tom
 */
public class ParseEventDispatcher implements ParseEventListener {

	private List<ParseEventListener> listeners = new ArrayList<ParseEventListener>();

	@Override
	public void handleEvent(ParseEvent event) {
		for (ParseEventListener listener : listeners) {
			listener.handleEvent(event);
		}
	}

	public void addListener(ParseEventListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(ParseEventListener listener) {
		this.listeners.remove(listener);
	}

}
