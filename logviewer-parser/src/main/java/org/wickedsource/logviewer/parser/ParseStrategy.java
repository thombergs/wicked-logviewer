package org.wickedsource.logviewer.parser;

import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;
import org.wickedsource.logviewer.parser.model.LogEntry;

/**
 * A {@link ParseStrategy} takes a context of a couple lines and parses them
 * after the rules defined by the concrete strategy implementation.
 * 
 * @author Tom
 * 
 */
public interface ParseStrategy {

	/**
	 * Parses the line currently focused in the given parse window (i.e. the
	 * line returned by window.getFocusLine()) and creates a {@link LogEntry}
	 * object from it.
	 * 
	 * @param window
	 *            the window providing the context for the parsing.
	 * @param eventListener
	 *            the event listener that is to receive all
	 *            {@link ParseEvent}s that occur during the parsing.
	 */
	void parseLine(ParseWindow window, ParseEventListener eventListener);

	/**
	 * This method determines if this strategy implementation has a valid
	 * solution for parsing the focused line in the given window (i.e. it
	 * checks, if the line(s) within the window match a pattern which this
	 * strategy implementation can work with).
	 * 
	 * @param window
	 *            the parsing window to check.
	 * @return true if this strategy implementation can successfully parse the
	 *         given window, false if not.
	 */
	boolean hasSolutionFor(ParseWindow window);

}
