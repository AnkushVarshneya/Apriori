/**
 * @(#)TableBuilder.java
 * @author Ankush Varshneya
 * @student# 100853074
 * Text based table builder
 */

import java.util.LinkedList;
import java.util.List;

public class TableBuilder {
	List<String[]> rows = new LinkedList<String[]>();
	String[] rowHeader;
	String title;

	public void setTitle(String t) {
		title = t;
	}

	public void setRowHeader(String... cols) {
		rowHeader = cols;
	}

	public void addRow(String... cols) {
		rows.add(cols);
	}

	private int[] colWidths() {
		int cols = -1;

		for (String[] row : rows)
			cols = Math.max(cols, row.length);

		int[] widths = new int[cols];

		for (int colNum = 0; colNum < rowHeader.length; colNum++)
			widths[colNum] = Math.max(widths[colNum], rowHeader[colNum] != null ? rowHeader[colNum].length() : 0);

		for (String[] row : rows)
			for (int colNum = 0; colNum < row.length; colNum++)
				widths[colNum] = Math.max(widths[colNum], row[colNum] != null ? row[colNum].length() : 0);

		return widths;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();

		if (rows == null || rows.isEmpty())
			return buf.toString();

		if (title != null) {
			buf.append(title);
			buf.append('\n');
		}
		
		int[] colWidths = colWidths();

		for (int colNum = 0; colNum < rowHeader.length; colNum++) {
			buf.append('+');
			buf.append(rightPad("", colWidths[colNum] + 2, "-"));
		}
		buf.append('+');
		buf.append('\n');

		for (int colNum = 0; colNum < rowHeader.length; colNum++) {
			buf.append('|');
			buf.append(' ');
			buf.append(rightPad(rowHeader[colNum] != null ? rowHeader[colNum] : "", colWidths[colNum], " "));
			buf.append(' ');
		}
		buf.append('|');
		buf.append('\n');

		for (int colNum = 0; colNum < rowHeader.length; colNum++) {
			buf.append('+');
			buf.append(rightPad("", colWidths[colNum] + 2, "="));
		}
		buf.append('+');
		buf.append('\n');

		for (String[] row : rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				buf.append('|');
				buf.append(' ');
				buf.append(rightPad(row[colNum] != null ? row[colNum] : "", colWidths[colNum], " "));
				buf.append(' ');
			}
			buf.append('|');
			buf.append('\n');
			for (int colNum = 0; colNum < rowHeader.length; colNum++) {
				buf.append('+');
				buf.append(rightPad("", colWidths[colNum] + 2, "-"));
			}
			buf.append('+');
			buf.append('\n');

		}

		return buf.toString();
	}

	private String rightPad(String string, int size, String filler) {
		if (string == null)
			return null;

		int pads = size - string.length();
		// returns original String when possible
		if (pads <= 0)
			return string;

		// else append it with space
		String ret = new String(string);
		for (int i = 0; i < pads; i++)
			ret += filler;
		return ret;
	}

	/**
	 * Testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		TableBuilder tb = new TableBuilder();
		tb.setTitle("ma");
		tb.setRowHeader("alpha", "beta", "gamma");
		tb.addRow("1", "20000000", "foo");
		tb.addRow("x", "yzz", "y");
		System.out.println(tb.toString());
	}
}