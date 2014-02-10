/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api.log;

import pl.poznan.put.cs.ify.api.types.YList;
import android.content.Context;
import android.util.Log;

public class YLog {
	public static final int LIST_MAX_SIZE = 25;

	@SuppressWarnings("unused")
	private YList<YLogEntry> mHistory;
	@SuppressWarnings("unused")
	private YLogView mLogView;

	private static YList<YLogEntry> history = null;
	private static YLogView logView;

	public YLog(Context ctx) {
		history = mHistory = new YLogEntryList();
		logView = mLogView = new YLogView(ctx);
	}

	public static void toggleView() {
		if (logView != null) {
			if (logView.isEnabled())
				logView.hide();
			else
				logView.show();
		}
	}

	public static void show() {
		if (logView != null)
			logView.show();
	}

	public static void hide() {
		if (logView != null)
			logView.hide();
	}

	public static boolean isVisible() {
		return logView.isEnabled();
	}

	public static YList<YLogEntry> getHistory() {
		return history;
	}

	public static YList<YLogEntry> getFilteredHistory(String tag) {
		return getFilteredHistory(tag, VERBOSE);
	}

	public static YList<YLogEntry> getFilteredHistory(String tag, int level) {
		YList<YLogEntry> filtered = new YLogEntryList();
		for (YLogEntry entry : history) {
			if (entry.mPriority >= level && entry.mTag.equals(tag)) {
				filtered.add(entry);
			}
		}
		return filtered;
	}

	public static final String[] NAMES = { "INVALID", "INVALID", "VERBOSE",
			"DEBUG", "INFO", "WARN", "ERROR", "ASSERT" };
	public static final String[] COLORS = { "#000000", "#000000", "#CCCCCC",
			"#4080FF", "#40FF40", "#FFC040", "#FF4040", "#FF00FF", };

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
			YLogEntry entry = new YLogEntry(priority, tag, msg);
			history.add(entry);
			if (logView != null)
				logView.add(entry);
		}
		Log.println(priority, "<Y>" + tag, msg);
	}

	public static void d(String tag, String msg) {
		println(DEBUG, tag, msg);
	}

	public static void e(String tag, String msg) {
		println(ERROR, tag, msg);
	}

	public static void i(String tag, String msg) {
		println(INFO, tag, msg);
	}

	public static void v(String tag, String msg) {
		println(VERBOSE, tag, msg);
	}

	public static void w(String tag, String msg) {
		println(WARN, tag, msg);
	}

	public static void wtf(String tag, String msg) {
		println(ASSERT, tag, msg);
	}
}
