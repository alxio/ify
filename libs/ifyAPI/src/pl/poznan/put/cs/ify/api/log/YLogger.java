package pl.poznan.put.cs.ify.api.log;

public class YLogger {
	private String mTag;

	public YLogger(String tag) {
		mTag = tag;
	}

	public void println(int priority, String msg) {
		YLog.println(priority, mTag, msg);
	}

	public void d(String msg) {
		YLog.d(mTag, msg);
	}

	public void e(String msg) {
		YLog.e(mTag, msg);
	}

	public void i(String msg) {
		YLog.i(mTag, msg);
	}

	public void v(String msg) {
		YLog.v(mTag, msg);
	}

	public void w(String msg) {
		YLog.w(mTag, msg);
	}

	public void wtf(String msg) {
		YLog.wtf(mTag, msg);
	}
}
