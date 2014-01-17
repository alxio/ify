package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YGeocoderEvent extends YEvent {

	private String mAddress;

	public YGeocoderEvent(String address) {
		mAddress = address;
	}

	/**
	 * @return Address of place.
	 */
	public String getAddress() {
		return mAddress;
	}

	@Override
	public long getId() {
		return Y.Geocoder;
	}

}
