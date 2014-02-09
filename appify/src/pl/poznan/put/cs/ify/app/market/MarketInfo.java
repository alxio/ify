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

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class MarketInfo implements Parcelable {
	private String name;
	private long timestamp;
	private String url;
	private Double rate;
	private String description;
	private int safe;
	private ArrayList<MarketComment> comments;

	public MarketInfo(String name, long timestamp, String url,
			String description, ArrayList<MarketComment> comments, int safe,
			Double rate) {
		super();
		this.name = name;
		this.timestamp = timestamp * 1000;
		this.url = url;
		this.description = description;
		this.comments = comments;
		this.safe = safe;
		this.rate = rate;
	}

	public ArrayList<MarketComment> getComments() {
		return comments;
	}

	public Double getRate() {
		return rate;
	}

	public boolean isSafe() {
		return safe != 0;
	}

	public String getName() {
		return name;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(safe);
		dest.writeString(name);
		dest.writeLong(timestamp);
		dest.writeString(url);
		dest.writeString(description);
		dest.writeArray(comments.toArray());
	}

	public MarketInfo(Parcel in) {
		safe = in.readInt();
		name = in.readString();
		timestamp = in.readLong();
		url = in.readString();
		description = in.readString();
		MarketComment[] temp = (MarketComment[]) in
				.readArray(MarketComment.CREATOR.getClass().getClassLoader());
		comments = (ArrayList<MarketComment>) Arrays.asList(temp);
	}

	public static final Parcelable.Creator<MarketInfo> CREATOR = new Parcelable.Creator<MarketInfo>() {
		public MarketInfo createFromParcel(Parcel in) {
			return new MarketInfo(in);
		}

		public MarketInfo[] newArray(int size) {
			return new MarketInfo[size];
		}
	};

}
