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
package pl.poznan.put.cs.ify.api.params;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * GPS position in format acceptable by group server and YRecipe params.
 */
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

	/**
	 * @return Distance to other point in meters.
	 */
	public float getDistance(YPosition other) {
		return getDistance(this, other);
	}

	/**
	 * @return Distance between points in meters.
	 */
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
