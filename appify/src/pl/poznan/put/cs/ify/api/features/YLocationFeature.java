package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
//TODO: Unimplemented
public class YLocationFeature extends YFeature {

	private LocationManager mLocationManager;
	private Location mLastLocation;

	@Override
	public String getName() {
		return "YLocationFeature";
	}

	@Override
	public void initialize(Context ctx, YReceiptsService srv) {
		mLocationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public LocationManager getLocationManager() {
		return mLocationManager;
	}

	@Override
	public void registerReceipt(YReceipt receipt) {
		// TODO Register in my triggers
	}

	@Override
	public void uninitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterReceipt(YReceipt receipt) {
		// TODO Auto-generated method stub
		
	}
}
  