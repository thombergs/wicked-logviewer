package org.wickedsource.logviewer.parser;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.listener.InMemoryCapturingListener;
import org.wickedsource.logviewer.parser.model.LogEntry;

public class JavaLogParserTest {

	private static List<LogEntry> logEntries;

	@BeforeClass
	public static void setup() {
		InputStream in = JavaLogParserTest.class.getResourceAsStream(JavaLogParserTest.class.getSimpleName() + ".log");
		LineDescriptor descriptor = new DefaultLineDescriptorFactory().createLogFileDescriptor();
		LogParser parser = new JavaLogParser(descriptor);
		InMemoryCapturingListener listener = new InMemoryCapturingListener();
		parser.parse(in, listener);
		logEntries = listener.getLogEntries();
	}

	@Test
	public void checkMultiLine() {
		LogEntry lastLogEntry = logEntries.get(logEntries.size() - 1);
		Assert.assertTrue(lastLogEntry.getMessage().contains("Multi-Line-Test"));
	}

	@Test
	public void checkResultNotNull() {
		Assert.assertNotNull(logEntries);
	}

	@Test
	public void checkResultCount() {
		Assert.assertEquals(13, logEntries.size());
	}

	@Test
	public void checkDateField() throws ParseException {
		LogEntry firstEntry = logEntries.get(0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S");
		Assert.assertEquals(dateFormat.parseObject("2013-08-05 07:36:08,918"), firstEntry.getTimestamp());
	}

	@Test
	public void checkSeverityField() {
		LogEntry firstEntry = logEntries.get(0);
		Assert.assertEquals("INFO", firstEntry.getSeverity());
	}

	@Test
	public void checkCategoryField() {
		LogEntry firstEntry = logEntries.get(0);
		Assert.assertEquals("[org.apache.axis2.deployment.ModuleDeployer]", firstEntry.getLogger());
	}

	@Test
	public void checkThreadField() {
		LogEntry firstEntry = logEntries.get(0);
		Assert.assertEquals("(main)", firstEntry.getCustomField("Thread"));
	}

	@Test
	public void checkMessageField() {
		LogEntry firstEntry = logEntries.get(0);
		Assert.assertEquals(
				"Deploying module: rampart-1.5.1 - file:/opt/evergabe/jboss-as/server/default/tmp/ak2t6-kvr5eg-hjz8wx34-1-hjz8xgkd-ak/axis2.war/WEB-INF/modules/rampart-1.5.1.mar",
				firstEntry.getMessage());
	}

	@Test
	public void checkStacktrace() {
		LogEntry stacktraceEntry = logEntries.get(5);
		Assert.assertTrue(stacktraceEntry.hasStackTrace());
		Assert.assertEquals(142, stacktraceEntry.getStackTrace().size());
	}

}
