package pl.poznan.put.cs.ify.app.market;

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
		this.ts = ts;
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
}
