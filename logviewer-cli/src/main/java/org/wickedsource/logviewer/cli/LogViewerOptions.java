package org.wickedsource.logviewer.cli;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class LogViewerOptions extends Options {

	private static final long serialVersionUID = 1L;

	public LogViewerOptions() {
		addOption(OptionBuilder.withArgName("file").hasArg(true).withDescription("input file with raw log data")
				.isRequired(true).create("in"));
		addOption(OptionBuilder.withArgName("file").hasArg(true).withDescription("output file for aggregated log data")
				.isRequired(true).create("out"));
		addOption(OptionBuilder.withArgName("regex").hasArg(true).withDescription("regular expression for a log line")
				.isRequired(true).create("regex"));
		addOption(OptionBuilder.withArgName("number").hasArg(true)
				.withDescription("group number of the log date within the regular expression").isRequired(true)
				.withType(Integer.valueOf(0)).create("dateGroup"));
		addOption(OptionBuilder.withArgName("number").hasArg(true)
				.withDescription("group number of the log severity within the regular expression").isRequired(true)
				.withType(Integer.valueOf(0)).create("severityGroup"));
		addOption(OptionBuilder.withArgName("number").hasArg(true)
				.withDescription("group number of the logger name within the regular expression").isRequired(true)
				.withType(Integer.valueOf(0)).create("loggerGroup"));
		addOption(OptionBuilder.withArgName("number").hasArg(true)
				.withDescription("group number of the log message within the regular expression").isRequired(true)
				.withType(Integer.valueOf(0)).create("messageGroup"));
		addOption(OptionBuilder.withArgName("pattern").hasArg(true)
				.withDescription("date pattern of the log date (default: 'yyyy-MM-dd HH:mm:ss,S')")
				.create("datePattern"));
	}

	public File getInputFile(CommandLine line) throws FileNotFoundException {
		String inputFile = line.getOptionValue("in");
		File file = new File(inputFile);
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		return file;
	}

	public File getOutputFile(CommandLine line) {
		String outputFile = line.getOptionValue("out");
		File file = new File(outputFile);
		return file;
	}

	public String getRegex(CommandLine line) {
		return line.getOptionValue("regex");
	}

	public int getDateGroup(CommandLine line) {
		return Integer.parseInt(line.getOptionValue("dateGroup"));
	}

	public int getSeverityGroup(CommandLine line) {
		return Integer.parseInt(line.getOptionValue("severityGroup"));
	}

	public int getMessageGroup(CommandLine line) {
		return Integer.parseInt(line.getOptionValue("messageGroup"));
	}

	public int getLoggerGroup(CommandLine line) {
		return Integer.parseInt(line.getOptionValue("loggerGroup"));
	}

	public String getDatePattern(CommandLine line) {
		String pattern = line.getOptionValue("datePattern");
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss,S";
		}
		return pattern;
	}
}
