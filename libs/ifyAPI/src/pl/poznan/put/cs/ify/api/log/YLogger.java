package pl.poznan.put.cs.ify.api.log;

import pl.poznan.put.cs.ify.api.IYRecipeHost;

public class YLogger {
	private String mTag;
	private IYRecipeHost mHost;

	public YLogger(String tag, IYRecipeHost host) {
		mTag = tag;
		mHost = host;
	}

	public void getLogs() {
		YLog.getFilteredHistory("<Y>" + mTag);
	}

	// TODO Refacetor d,e, etc to use it
	public void println(int priority, String msg) {
		YLog.println(priority, mTag, msg);
		mHost.sendLogs(mTag);
	}

	public void d(String msg) {
		println(YLog.DEBUG, msg);
	}

	public void e(String msg) {
		println(YLog.ERROR, msg);
	}

	public void i(String msg) {
		println(YLog.INFO, msg);
	}

	public void v(String msg) {
		println(YLog.VERBOSE, msg);
	}

	public void w(String msg) {
		println(YLog.WARN, msg);
	}

	public void wtf(String msg) {
		println(YLog.ASSERT, msg);
	}
}
