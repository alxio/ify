package pl.poznan.put.cs.ify.api.types;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class YArrayList<P extends Parcelable> extends ArrayList<P> implements YList<P> {
	public YArrayList() {
		super();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.size());
		for (P entry : this) {
			dest.writeParcelable(entry, 0);
		}
	}

	public YArrayList(Parcel in) {
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			P entry = in.readParcelable(null);
			add(entry);
		}
	}

	public static final Parcelable.Creator<YArrayList> CREATOR = new Parcelable.Creator<YArrayList>() {
		public YArrayList createFromParcel(Parcel in) {
			return new YArrayList(in);
		}

		public YArrayList[] newArray(int size) {
			return new YArrayList[size];
		}
	};
}
