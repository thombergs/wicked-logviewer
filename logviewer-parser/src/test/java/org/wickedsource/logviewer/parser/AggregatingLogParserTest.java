package org.wickedsource.logviewer.parser;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.listener.AggregatingListener;
import org.wickedsource.logviewer.parser.model.AggregatedLogEntry;
import org.wickedsource.logviewer.parser.model.LogEntry;

public class AggregatingLogParserTest {

	private static List<AggregatedLogEntry> logEntries;

	@BeforeClass
	public static void setup() {
		InputStream in = AggregatingLogParserTest.class.getResourceAsStream(AggregatingLogParserTest.class.getSimpleName() + ".log");
		LineDescriptor descriptor = new DefaultLineDescriptorFactory().createLogFileDescriptor();
		LogParser parser = new JavaLogParser(descriptor);
		AggregatingListener listener = new AggregatingListener();
		parser.parse(in, listener);
		logEntries = listener.getLogEntries();
	}

	@Test
	public void test(){
		
	}
	
	
}
