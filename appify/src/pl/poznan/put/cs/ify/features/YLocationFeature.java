package pl.poznan.put.cs.ify.features;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import pl.poznan.put.cs.ify.core.YFeature;

public class YLocationFeature implements YFeature {

	private LocationManager mLocationManager;

	@Override
	public String getName() {
		return "YGps";
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
