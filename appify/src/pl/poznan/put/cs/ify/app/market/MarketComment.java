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
package pl.poznan.put.cs.ify.app.market;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class MarketComment implements Parcelable {
	private String name;
	private String content;
	private long ts;
	private int id;

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public MarketComment(long ts, String comment, int id, String name) {
		super();
		this.name = name;
		this.content = comment;
		this.ts = ts * 1000;
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(content);
		dest.writeLong(ts);
		dest.writeInt(id);
	}

	public MarketComment(Parcel in) {
		name = in.readString();
		content = in.readString();
		ts = in.readLong();
		id = in.readInt();
	}

	public static final Parcelable.Creator<MarketComment> CREATOR = new Parcelable.Creator<MarketComment>() {
		public MarketComment createFromParcel(Parcel in) {
			return new MarketComment(in);
		}

		public MarketComment[] newArray(int size) {
			return new MarketComment[size];
		}
	};

	public long getTimestamp() {
		return ts;
	}
}
