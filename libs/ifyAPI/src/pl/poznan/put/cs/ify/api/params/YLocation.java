package pl.poznan.put.cs.ify.api.params;

import android.location.Location;

public class YLocation extends Location {

	public YLocation(Location l) {
		super(l);
	}

	public YLocation(String provider) {
		super(provider);
	}
}
