package pl.poznan.put.cs.ify.api.log;

import android.text.format.Time;

public class YLogEntry {
	int mPriority;
	String mTag;
	String mMessage;
	String mTime;

	public YLogEntry(int priority, String tag, String msg) {
		mPriority = priority;
		mTag = tag;
		mMessage = msg;
		int time = (int) (System.currentTimeMillis() / 1000);
		Time now = new Time();
		now.setToNow();
		mTime = now.format("%H:%M:%S");
	}

	public String toString() {
		return "(" + YLog.NAMES[mPriority] + ") " + mTag + ", " + mMessage;
	}

	// Usage:
	// mBox = new TextView(context);
	// mBox.setText(Html.fromHtml(logEntry.toHtml()));
	public String toHtml() {
		return "<font color=\"" + mTime + " " + YLog.COLORS[mPriority] + "\" face=\"monospace\"><b>" + mTag + ": </b>"
				+ mMessage + "</font>";
	}
}
