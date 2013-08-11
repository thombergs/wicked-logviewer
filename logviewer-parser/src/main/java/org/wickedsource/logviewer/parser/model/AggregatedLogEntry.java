package org.wickedsource.logviewer.parser.model;

import java.util.Comparator;

public class AggregatedLogEntry extends LogEntry {

	private Integer count = 1;

	public AggregatedLogEntry() {

	}

	public AggregatedLogEntry(LogEntry that) {
		this.setCustomFields(that.getCustomFields());
		this.setFirstStacktraceLine(that.getFirstStacktraceLine());
		this.setLineNumber(that.getLineNumber());
		this.setLogger(that.getLogger());
		this.setMessage(that.getMessage());
		this.setSeverity(that.getSeverity());
		this.setStackTrace(that.getStackTrace());
	}

	public Integer getAggregateCount() {
		return count;
	}

	public void setAggregateCount(Integer count) {
		this.count = count;
	}

	/**
	 * Creates a {@link Comparator} that sorts {@link AggregatedLogEntry}s in
	 * descending order.
	 */
	public static Comparator<? super AggregatedLogEntry> comparator() {
		return new Comparator<AggregatedLogEntry>() {
			@Override
			public int compare(AggregatedLogEntry o1, AggregatedLogEntry o2) {
				return o2.getAggregateCount().compareTo(o1.getAggregateCount());
			}
		};
	}

}
