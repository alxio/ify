package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YWifiEvent extends YEvent {
	public static final long ID = Y.Wifi;

	@Override
	public long getId() {
		return ID;
	}
}
