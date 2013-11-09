package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.params.YLocation;

public class YGPSEvent extends YEvent {
	private YLocation mLocation;

	public YGPSEvent(YLocation loc) {
		mLocation = loc;
	}

	public YLocation getLocation() {
		return mLocation;
	}

	@Override
	public int getId() {
		return Y.GPS;
	}
}
