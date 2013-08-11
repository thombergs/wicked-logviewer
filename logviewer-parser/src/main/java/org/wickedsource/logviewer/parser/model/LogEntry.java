package org.wickedsource.logviewer.parser.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogEntry {

	private int lineNumber;

	private Date timestamp;

	private String severity;

	private String message;

	private String logger;

	private String firstStacktraceLine;

	private List<StackTraceElement> stackTrace = new ArrayList<StackTraceElement>();

	private Map<String, String> customFields = new HashMap<String, String>();

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<StackTraceElement> getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(List<StackTraceElement> stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getCustomField(String key) {
		return this.customFields.get(key);
	}

	public void setCustomField(String key, String value) {
		this.customFields.put(key, value);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String toString() {
		return String.format("line %d", lineNumber);
	}

	public String getFirstStacktraceLine() {
		return firstStacktraceLine;
	}

	public void setFirstStacktraceLine(String firstStacktraceLine) {
		this.firstStacktraceLine = firstStacktraceLine;
	}

	public boolean hasStackTrace() {
		return !this.stackTrace.isEmpty();
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	protected Map<String, String> getCustomFields() {
		return this.customFields;
	}

	protected void setCustomFields(Map<String, String> customFields) {
		this.customFields = customFields;
	}

}
