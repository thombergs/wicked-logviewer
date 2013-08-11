package org.wickedsource.logviewer.cli;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;

public class ConsoleLogViewer {

	public static void main(String[] args) {
		LogViewerOptions options = new LogViewerOptions();
		HelpFormatter formatter = new HelpFormatter();
		try {
			CommandLineParser cliParser = new BasicParser();
			CommandLine line = cliParser.parse(options, args);
			LineDescriptor descriptor = new LineDescriptor();
			File inputFile = null;

			try {
				inputFile = options.getInputFile(line);
			} catch (FileNotFoundException e) {
				System.err.println(String.format("File %s does not exist. Please provide an existing input file.",
						e.getMessage()));
				formatter.printHelp("logviewer", new LogViewerOptions());
			}

			File outputFile = options.getOutputFile(line);
			descriptor.setLineRegex(options.getRegex(line));
			descriptor.setDatePattern(options.getDatePattern(line));

			try {
				int dateGroup = options.getDateGroup(line);
				descriptor.setDateGroup(dateGroup);
			} catch (NumberFormatException e) {
				System.err
						.println(String.format("Please provide valid group number for the log date.", e.getMessage()));
				formatter.printHelp("logviewer", new LogViewerOptions());
			}
			try {
				int messageGroup = options.getMessageGroup(line);
				descriptor.setMessageGroup(messageGroup);
			} catch (NumberFormatException e) {
				System.err.println(String.format("Please provide valid group number for the log message.",
						e.getMessage()));
				formatter.printHelp("logviewer", new LogViewerOptions());
			}
			try {
				int severityGroup = options.getSeverityGroup(line);
				descriptor.setSeverityGroup(severityGroup);
			} catch (NumberFormatException e) {
				System.err.println(String.format("Please provide valid group number for the log severity.",
						e.getMessage()));
				formatter.printHelp("logviewer", new LogViewerOptions());
			}
			try {
				int loggerGroup = options.getLoggerGroup(line);
				descriptor.setLoggerGroup(loggerGroup);
			} catch (NumberFormatException e) {
				System.err.println(String.format("Please provide valid group number for the logger.", e.getMessage()));
				formatter.printHelp("logviewer", new LogViewerOptions());
			}

			descriptor.addIgnorePattern("^Fehlercode.*$");
			descriptor.addIgnorePattern("^Datum.*$");
			descriptor.addIgnorePattern("^Fehler-ID.*$");
			descriptor.addIgnorePattern("^Interface.*$");
			descriptor.addIgnorePattern("^ *$");

			ConsoleLogAggregator aggregator = new ConsoleLogAggregator(inputFile, outputFile, descriptor);
			aggregator.aggregate();

		} catch (ParseException e) {
			System.err.println("Illegal parameters.");
			formatter.printHelp("logviewer", new LogViewerOptions());
		}

	}

}
