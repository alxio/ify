package pl.poznan.put.cs.ify.api.log;

import pl.poznan.put.cs.ify.api.IYReceiptHost;

public class YLogger {
	private String mTag;
	private IYReceiptHost mHost;

	public YLogger(String tag, IYReceiptHost host) {
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
		YLog.d(mTag, msg);
		mHost.sendLogs(mTag);
	}

	public void e(String msg) {
		YLog.e(mTag, msg);
		mHost.sendLogs(mTag);
	}

	public void i(String msg) {
		YLog.i(mTag, msg);
		mHost.sendLogs(mTag);
	}

	public void v(String msg) {
		YLog.v(mTag, msg);
		mHost.sendLogs(mTag);
	}

	public void w(String msg) {
		YLog.w(mTag, msg);
		mHost.sendLogs(mTag);
	}

	public void wtf(String msg) {
		YLog.wtf(mTag, msg);
		mHost.sendLogs(mTag);
	}
}
