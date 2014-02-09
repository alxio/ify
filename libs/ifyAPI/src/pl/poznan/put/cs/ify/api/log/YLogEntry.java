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

	public String timeAndMessage() {
		return mTime + ": " + mMessage;
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
