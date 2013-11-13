package pl.poznan.put.cs.ify.api.log;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

public class YLogEntry implements Parcelable {
	protected int mPriority;
	protected String mTag;
	protected String mMessage;
	protected String mTime;

	public YLogEntry(int priority, String tag, String msg) {
		mPriority = priority;
		mTag = tag;
		mMessage = msg;
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
		return mTime + " <font color=\"" + YLog.COLORS[mPriority] + "\" face=\"monospace\"><b>" + mTag + ": </b>"
				+ mMessage + "</font>";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mPriority);
		dest.writeString(mTag);
		dest.writeString(mMessage);
		dest.writeString(mTime);
	}

	public YLogEntry(Parcel in) {
		mPriority = in.readInt();
		mTag = in.readString();
		mMessage = in.readString();
		mTime = in.readString();
	}

	public static final Parcelable.Creator<YLogEntry> CREATOR = new Parcelable.Creator<YLogEntry>() {
		public YLogEntry createFromParcel(Parcel in) {
			return new YLogEntry(in);
		}

		public YLogEntry[] newArray(int size) {
			return new YLogEntry[size];
		}
	};
}
