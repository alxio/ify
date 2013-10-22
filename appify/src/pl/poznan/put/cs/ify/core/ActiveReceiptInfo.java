package pl.poznan.put.cs.ify.core;

import android.os.Parcel;
import android.os.Parcelable;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class ActiveReceiptInfo implements Parcelable {

	private String name;
	private YParamList params;

	public ActiveReceiptInfo(String name, YParamList params) {
		super();
		this.name = name;
		this.params = params;
	}

	public ActiveReceiptInfo(Parcel in) {
		this.name = in.readString();
		this.params = in.readParcelable(YParamList.class.getClassLoader());
	}

	public String getName() {
		return name;
	}

	public YParamList getParams() {
		return params;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeParcelable(params, 0);
	}

	public static final Parcelable.Creator<ActiveReceiptInfo> CREATOR = new Parcelable.Creator<ActiveReceiptInfo>() {
		public ActiveReceiptInfo createFromParcel(Parcel in) {
			return new ActiveReceiptInfo(in);
		}

		public ActiveReceiptInfo[] newArray(int size) {
			return new ActiveReceiptInfo[size];
		}
	};

}
