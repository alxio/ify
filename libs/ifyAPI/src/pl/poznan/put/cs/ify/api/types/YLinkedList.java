package pl.poznan.put.cs.ify.api.types;

import java.util.LinkedList;

import android.os.Parcel;
import android.os.Parcelable;

public class YLinkedList<P extends Parcelable> extends LinkedList<P> implements YList<P> {
	public YLinkedList() {
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

	public YLinkedList(Parcel in) {
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			P entry = in.readParcelable(null);
			add(entry);
		}
	}

	public static final Parcelable.Creator<YLinkedList> CREATOR = new Parcelable.Creator<YLinkedList>() {
		public YLinkedList createFromParcel(Parcel in) {
			return new YLinkedList(in);
		}

		public YLinkedList[] newArray(int size) {
			return new YLinkedList[size];
		}
	};
}
