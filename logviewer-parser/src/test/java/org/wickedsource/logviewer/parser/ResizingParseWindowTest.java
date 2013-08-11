package org.wickedsource.logviewer.parser;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wickedsource.logviewer.parser.ResizingParseWindow;

public class ResizingParseWindowTest {

	private static String[] expectedLines = new String[] { "line1", "line2", "line3", "line4", "line5", "line6",
			"line7", "line8", "line9", "line10" };

	private ResizingParseWindow window;

	@Before
	public void setup() {
		window = new ResizingParseWindow(ResizingParseWindowTest.class.getResourceAsStream("SlidingParseWindowTest.log"));
	}

	@Test
	public void testSlideThrough() throws IOException {
		String focusLine = null;
		String[] readLines = new String[10];
		int i = 0;
		while ((focusLine = window.slideForward()) != null) {
			readLines[i] = focusLine;
			i++;
		}
		Assert.assertArrayEquals(expectedLines, readLines);
	}

	@Test
	public void testLookAheadOneLine() throws IOException {
		testLookAheadNLines(1);
	}

	@Test
	public void testLookAheadThreeLines() throws IOException {
		testLookAheadNLines(3);
	}
	
	private void testLookAheadNLines(int n) throws IOException {
		String focusLine = null;
		String[] readLines = new String[10];
		int i = 0;
		while ((focusLine = window.slideForward()) != null) {
			readLines[i] = focusLine;
			String nextLine = window.getFutureLine(n);
			if (nextLine != null) {
				Assert.assertEquals(expectedLines[i + n], nextLine);
			}
			i++;
		}
		Assert.assertArrayEquals(expectedLines, readLines);

	}
}
