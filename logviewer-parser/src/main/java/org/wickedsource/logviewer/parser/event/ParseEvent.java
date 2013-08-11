package org.wickedsource.logviewer.parser.event;

public abstract class ParseEvent {

	protected final int lineNumber;

	public ParseEvent(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

}
