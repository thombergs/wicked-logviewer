package org.wickedsource.logviewer.parser;

import java.io.InputStream;
import java.util.regex.PatternSyntaxException;

import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.listener.ParseEventListener;

/**
 * A parser capable of parsing java log files containing stacktraces.
 * 
 * @author Tom
 * 
 */
public class JavaLogParser extends MultiStrategyLogParser implements LogParser {

	private LineDescriptor descriptor;

	public JavaLogParser(LineDescriptor descriptor) {
		this.descriptor = descriptor;
		addParseStrategy(new StacktraceParseStrategy(descriptor));
		addParseStrategy(new MultiLineParseStrategy(descriptor));
		addParseStrategy(new SimpleParseStrategy(descriptor));
	}

	@Override
	public void parse(InputStream in, ParseEventListener listener) {
		try {
			ResizingParseWindow window = new ResizingParseWindow(in);
			for(String ignorePattern : descriptor.getIgnorePatterns()){
				window.addIgnorePattern(ignorePattern);
			}
			
			while (window.slideForward() != null) {
				parseLine(window, listener);
			}
		} catch (PatternSyntaxException e) {
			throw new ParseException("LogFileDescriptor contains an invalid regex to describe a log line!", e);
		}
	}

}
