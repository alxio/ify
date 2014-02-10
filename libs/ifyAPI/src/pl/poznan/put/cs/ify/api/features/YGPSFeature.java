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
package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YGPSEvent;
import pl.poznan.put.cs.ify.api.params.YLocation;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Feature for reading position from GPS.
 */
public class YGPSFeature extends YFeature {

	public static final long ID = Y.GPS;
	private LocationManager mLocationManager;
	private LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				YLocation loc = new YLocation(location);
				YGPSEvent event = new YGPSEvent(loc);
				mLastLoc = loc;
				sendNotification(event);
			}
		}
	};
	private YLocation mLastLoc;
	private static final long TIME_INTERVAL = 60000;
	private static float DISTANCE_INTERVAL = 0;

	@Override
	public long getId() {
		return ID;
	}

	public YLocation getLastLocation() {
		return mLastLoc;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		Context context = srv.getContext();
		mLocationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				TIME_INTERVAL, DISTANCE_INTERVAL, mLocationListener);
	}

	@Override
	public void uninitialize() {
		mLocationManager.removeUpdates(mLocationListener);
	}
}
