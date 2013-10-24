package pl.poznan.put.cs.ify.api.log;

import java.util.LinkedList;
import java.util.List;

import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.util.Log;

public class YLog {

	private static final int LIST_MAX_SIZE = 500;
	private static List<YLogEntry> history = null;
	private List<YLogEntry> mHistory;

	private YReceiptsService mService;

	public YLog(YReceiptsService srv) {
		history = mHistory = new LinkedList<YLogEntry>();
		mService = srv;
	}

	public String getHtml() {
		StringBuilder html = new StringBuilder();
		for (YLogEntry entry : mHistory) {
			html.append(entry.toHtml());
			html.append("<br>");
		}
		return html.toString();
	}

	public static final String[] NAMES = { "INVALID", "INVALID", "VERBOSE", "DEBUG", "INFO", "WARN", "ERROR", "ASSERT" };
	public static final String[] COLORS = { "#000000", "#000000", "#000000", "#0000FF", "#00FF00", "#FF8000",
			"#FF0000", "#FF00FF", };

	public static final int VERBOSE = 2;
	public static final int DEBUG = 3;
	public static final int INFO = 4;
	public static final int WARN = 5;
	public static final int ERROR = 6;
	public static final int ASSERT = 7;

	public static void println(int priority, String tag, String msg) {
		if (history != null) {
			if (history.size() >= LIST_MAX_SIZE)
				history.remove(0);
			history.add(new YLogEntry(priority, tag, msg));
		}
		Log.println(priority, tag, msg);
	}

	public static void d(String tag, String msg) {
		println(DEBUG, tag, msg);
	}

	public static void e(String tag, String msg) {
		println(DEBUG, tag, msg);
	}

	public static void i(String tag, String msg) {
		println(DEBUG, tag, msg);
	}

	public static void v(String tag, String msg) {
		println(DEBUG, tag, msg);
	}

	public static void w(String tag, String msg) {
		println(DEBUG, tag, msg);
	}

	public static void wtf(String tag, String msg) {
		println(DEBUG, tag, msg);
	}
}
