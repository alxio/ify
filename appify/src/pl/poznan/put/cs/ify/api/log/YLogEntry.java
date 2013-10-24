package pl.poznan.put.cs.ify.api.log;

public class YLogEntry {
	int mPriority;
	String mTag;
	String mMessage;

	public YLogEntry(int priority, String tag, String msg) {
		mPriority = priority;
		mTag = tag;
		mMessage = msg;
	}

	public String toString() {
		return "(" + YLog.NAMES[mPriority] + ") " + mTag + ", " + mMessage;
	}

	// Usage:
	// mBox = new TextView(context);
	// mBox.setText(Html.fromHtml(logEntry.toHtml()));
	public String toHtml() {
		return "<font color=\"" + YLog.COLORS[mPriority] + "\" face=\"monospace\"><b>" + mTag + ": </b>" + mMessage
				+ "</font>";
	}
}
