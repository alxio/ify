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

import java.util.ArrayList;

import pl.poznan.put.cs.ify.api.types.YList;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class YLogEntryList extends ArrayList<YLogEntry> implements YList<YLogEntry> {
	private static final long serialVersionUID = 1L;

	public YLogEntryList() {
		super();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.size());
		for (YLogEntry entry : this) {
			dest.writeParcelable(entry, 0);
		}
	}

	public YLogEntryList(Parcel in) {
		int size = in.readInt();
		Log.d("SIZE", "" + size);
		for (int i = 0; i < size; i++) {
			YLogEntry entry = in.readParcelable(YLogEntry.class.getClassLoader());
			add(entry);
		}
	}

	public static final Parcelable.Creator<YLogEntryList> CREATOR = new Parcelable.Creator<YLogEntryList>() {
		public YLogEntryList createFromParcel(Parcel in) {
			return new YLogEntryList(in);
		}

		public YLogEntryList[] newArray(int size) {
			return new YLogEntryList[size];
		}
	};

	public String toHTML() {
		StringBuilder sb = new StringBuilder();
		for (YLogEntry l : this) {
			sb.append(l.toHtml());
			sb.append("<br>");
		}
		return sb.toString();
	}

	public String timeAndMessages() {
		StringBuilder sb = new StringBuilder();
		for (YLogEntry l : this) {
			sb.append(l.timeAndMessage());
			sb.append("\n");
		}
		return sb.toString();
	}
}
