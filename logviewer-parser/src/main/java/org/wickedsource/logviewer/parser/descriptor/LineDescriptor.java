package org.wickedsource.logviewer.parser.descriptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LineDescriptor {

	private String lineRegex;

	private int loggerGroup;

	private int dateGroup;

	private int severityGroup;

	private int messageGroup;

	private String datePattern;

	private DateFormat dateFormat;

	private List<String> ignorePatterns = new ArrayList<String>();

	private List<CustomFieldDescriptor> customFieldDescriptors = new ArrayList<CustomFieldDescriptor>();

	public String getLineRegex() {
		return lineRegex;
	}

	public void setLineRegex(String lineRegex) {
		this.lineRegex = lineRegex;
	}

	public int getDateGroup() {
		return dateGroup;
	}

	public void setDateGroup(int dateGroup) {
		this.dateGroup = dateGroup;
	}

	public int getSeverityGroup() {
		return severityGroup;
	}

	public void setSeverityGroup(int severityGroup) {
		this.severityGroup = severityGroup;
	}

	public int getMessageGroup() {
		return messageGroup;
	}

	public void setMessageGroup(int messageGroup) {
		this.messageGroup = messageGroup;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
		this.dateFormat = new SimpleDateFormat(datePattern);
	}

	public List<CustomFieldDescriptor> getCustomFieldDescriptors() {
		return customFieldDescriptors;
	}

	public void setCustomFieldDescriptors(List<CustomFieldDescriptor> customFieldDescriptors) {
		this.customFieldDescriptors = customFieldDescriptors;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public int getLoggerGroup() {
		return loggerGroup;
	}

	public void setLoggerGroup(int loggerGroup) {
		this.loggerGroup = loggerGroup;
	}

	public List<String> getIgnorePatterns() {
		return ignorePatterns;
	}

	public void setIgnorePatterns(List<String> ignorePatterns) {
		this.ignorePatterns = ignorePatterns;
	}

	public void addIgnorePattern(String ignorePattern) {
		this.ignorePatterns.add(ignorePattern);
	}

}
