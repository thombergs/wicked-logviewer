package org.wickedsource.logviewer.parser;

import java.util.ArrayList;
import java.util.List;

import org.wickedsource.logviewer.parser.event.LineNotParsedEvent;
import org.wickedsource.logviewer.parser.event.LineNotParsedEvent.Reason;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;

/**
 * This implementation of {@link LogParser} can be configured with
 * multiple {@link ParseStrategy}s to be used while parsing.
 * <p/>
 * Implementing classes must initialize the {@link ParseStrategy} by calling
 * {@link #addParseStrategy(ParseStrategy)} in the constructor. The registered
 * {@link ParseStrategy} will then be used when the implementing class calls
 * {@link #parseLine(ParseWindow, ParseEventListener)}.
 * 
 * @author Tom
 * 
 */
public abstract class MultiStrategyLogParser {

	private List<ParseStrategy> strategies = new ArrayList<ParseStrategy>();

	/**
	 * Adds a {@link ParseStrategy} to this parser. Note that the order of the
	 * strategies is important! The parser will try to find a matching strategy
	 * for each log line. The first strategy to be added by
	 * {@link #addParseStrategy(ParseStrategy)} will be asked first and so on.
	 * 
	 * @param strategy
	 *            the {@link ParseStrategy} to add to this parser.
	 */
	public void addParseStrategy(ParseStrategy strategy) {
		strategies.add(strategy);
	}

	protected void parseLine(ParseWindow window, ParseEventListener listener) {
		boolean parsed = false;
		for (ParseStrategy strategy : this.strategies) {
			if (strategy.hasSolutionFor(window)) {
				strategy.parseLine(window, listener);
				parsed = true;
				break;
			}
		}
		if (!parsed) {
			listener.handleEvent(new LineNotParsedEvent(window.getFocusLineNumber(), Reason.NO_MATCHING_PARSE_STRATEGY));
		}
	}

}
