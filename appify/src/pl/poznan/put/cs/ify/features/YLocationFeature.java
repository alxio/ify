package pl.poznan.put.cs.ify.features;

import pl.poznan.put.cs.ify.core.YFeature;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class YLocationFeature implements YFeature {

	private LocationManager mLocationManager;
	private Location mLastLocation;

	@Override
	public String getName() {
		return "YLocationFeature";
	}

	@Override
	public void initialize(Context ctx) {
		mLocationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public LocationManager getLocationManager() {
		return mLocationManager;
	}
}
