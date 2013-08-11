package org.wickedsource.logviewer.parser;

import java.io.InputStream;

import org.wickedsource.logviewer.parser.event.ParseEvent;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;

/**
 * This is the interface to be implemented by log parsers that load all parsing
 * results into memory and return it to the client.
 * 
 * @author Tom
 * 
 */
public interface LogParser {

	/**
	 * Parses the given input stream. The results are not returned by this
	 * function but instead propagated via {@link ParseEvent}s to the given
	 * {@link ParseEventListener}.
	 * 
	 * @param in
	 *            the input stream to parse
	 * @param listener
	 *            the {@link ParseEventListener} that processes all
	 *            {@link ParseEvent}s that occur while parsing.
	 */
	void parse(InputStream in, ParseEventListener listener);

}