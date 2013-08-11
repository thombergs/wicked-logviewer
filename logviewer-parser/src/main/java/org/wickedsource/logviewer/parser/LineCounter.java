package org.wickedsource.logviewer.parser;

import java.io.IOException;
import java.io.InputStream;

public class LineCounter {

	private InputStream in;

	private Integer count;

	public LineCounter(InputStream in) {
		this.in = in;
	}

	public int getLineCount() {
		if (count == null) {
			try {
				byte[] c = new byte[1024];
				int count = 0;
				int readChars = 0;
				boolean empty = true;
				while ((readChars = in.read(c)) != -1) {
					empty = false;
					for (int i = 0; i < readChars; ++i) {
						if (c[i] == '\n' || c[i] == '\r' || c[i] == '\u0085' || c[i] == '\u2028' || c[i] == '\u2029') {
							++count;
						}
					}
				}
				return (count == 0 && !empty) ? 1 : count;
			} catch (IOException e) {
				throw new ParseException(e);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					throw new ParseException(e);
				}
			}
		} else {
			return count;
		}

	}

}
