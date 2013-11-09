package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YGeocoderEvent extends YEvent {

	private String mAddress;

	public YGeocoderEvent(String address) {
		mAddress = address;
	}

	public String getAddress() {
		return mAddress;
	}

	@Override
	public int getId() {
		return Y.Geocoder;
	}

}
