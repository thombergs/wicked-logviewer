package org.wickedsource.logviewer.cli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.wickedsource.logviewer.parser.JavaLogParser;
import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;
import org.wickedsource.logviewer.parser.listener.AggregatingListener;
import org.wickedsource.logviewer.parser.listener.ParseEventDispatcher;
import org.wickedsource.logviewer.parser.model.AggregatedLogEntry;

public class ConsoleLogAggregator {

	private File input;

	private File output;

	private LineDescriptor lineDescriptor;

	public ConsoleLogAggregator(File input, File output, LineDescriptor lineDescriptor) {
		this.input = input;
		this.output = output;
		this.lineDescriptor = lineDescriptor;
	}

	public void aggregate() {
		try {
			JavaLogParser parser = new JavaLogParser(lineDescriptor);
			AggregatingListener aggregatingListener = new AggregatingListener();
			FailureLoggingListener failLogListener = new FailureLoggingListener();
			ParseEventDispatcher dispatcher = new ParseEventDispatcher();
			dispatcher.addListener(aggregatingListener);
			dispatcher.addListener(failLogListener);
			InputStream in = new FileInputStream(input);
			parser.parse(in, dispatcher);

			OutputStream out = new FileOutputStream(output);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			for (AggregatedLogEntry entry : aggregatingListener.getLogEntries()) {
				writer.write(String.format("%s %s %s %s %s\n", entry.getAggregateCount(),
						entry.hasStackTrace() ? "STACKTRACE" : "NO_STACKTRACE", entry.getSeverity(), entry.getLogger(),
						entry.getMessage()));
			}
			in.close();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
