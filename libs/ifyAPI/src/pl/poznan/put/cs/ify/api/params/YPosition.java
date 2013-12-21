package pl.poznan.put.cs.ify.api.params;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class YPosition implements Parcelable {

	public final double lat;
	public final double lng;
	public final int radius;

	public YPosition(double lat, double lng, int radius) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.radius = radius;
	}

	public float getDistance(YPosition other) {
		return getDistance(this, other);
	}

	public static float getDistance(YPosition pos1, YPosition pos2) {
		float[] tmp = new float[1];
		Location.distanceBetween(pos1.lat, pos1.lng, pos2.lat, pos2.lng, tmp);
		return tmp[0];
	}

	public YPosition(Parcel in) {
		lat = in.readDouble();
		lng = in.readDouble();
		radius = in.readInt();
	}

	@Override
	public String toString() {
		return lat + ";" + lng + ";" + radius;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(lat);
		dest.writeDouble(lng);
		dest.writeInt(radius);
	}

	public static final Parcelable.Creator<YPosition> CREATOR = new Parcelable.Creator<YPosition>() {
		public YPosition createFromParcel(Parcel in) {
			return new YPosition(in);
		}

		public YPosition[] newArray(int size) {
			return new YPosition[size];
		}
	};
}
