package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.params.YLocation;
import pl.poznan.put.cs.ify.api.params.YPosition;

public class YGPSEvent extends YEvent {
	private YLocation mLocation;

	public YGPSEvent(YLocation loc) {
		mLocation = loc;
	}

	public YLocation getLocation() {
		return mLocation;
	}

	public YPosition getPosition() {
		return new YPosition(mLocation.getLatitude(), mLocation.getLongitude(), 0);
	}

	@Override
	public long getId() {
		return Y.GPS;
	}
}
