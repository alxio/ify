package cs.put.poznan.pl.summoner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class UserInfo implements Parcelable {

	private LatLng mLatLgn;
	private String mName;
	private String mMessage;
	private Long mUpdatedAtPos;
	private Long mUpdatedAtMsg;

	public UserInfo(String name, String message, long updatedAtPos,
			LatLng latLng, long updatedAtMsg) {
		mName = name;
		mMessage = message;
		mUpdatedAtPos = updatedAtPos;
		mLatLgn = latLng;
		mUpdatedAtMsg = updatedAtMsg;
	}

	public UserInfo(String name) {
		mName = name;
	}

	public void setLatLgn(LatLng mLatLgn) {
		this.mLatLgn = mLatLgn;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public void setUpdatedAtPos(Long mUpdatedAtPos) {
		this.mUpdatedAtPos = mUpdatedAtPos;
	}

	public void setUpdatedAtMsg(Long mUpdatedAtMsg) {
		this.mUpdatedAtMsg = mUpdatedAtMsg;
	}

	public String getName() {
		return mName + "";
	}

	public Long getUpdatedAtPos() {
		return mUpdatedAtPos;
	}

	public Long getUpdatedAtMsg() {
		return mUpdatedAtMsg;
	}

	public String getMessage() {
		return mMessage + "";
	}

	public LatLng getLatLng() {
		return mLatLgn;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeLong(mUpdatedAtMsg);
		dest.writeString(mMessage);
		dest.writeLong(mUpdatedAtPos);
		dest.writeDouble(mLatLgn.latitude);
		dest.writeDouble(mLatLgn.longitude);
	}

	public UserInfo(Parcel in) {
		mName = in.readString();
		mUpdatedAtMsg = in.readLong();
		mMessage = in.readString();
		mUpdatedAtPos = in.readLong();
		double lat = in.readDouble();
		double lng = in.readDouble();
		mLatLgn = new LatLng(lat, lng);
	}

	public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
		public UserInfo createFromParcel(Parcel in) {
			return new UserInfo(in);
		}

		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}
	};
}
