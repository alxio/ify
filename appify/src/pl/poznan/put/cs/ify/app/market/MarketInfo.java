package pl.poznan.put.cs.ify.app.market;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class MarketInfo implements Parcelable {
	private String name;
	private long timestamp;
	private String url;
	private double rate;
	private String description;
	private int safe;
	private ArrayList<MarketComment> comments;

	public MarketInfo(String name, long timestamp, String url,
			String description, ArrayList<MarketComment> comments, int safe,
			double rate) {
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

	public double getRate() {
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
