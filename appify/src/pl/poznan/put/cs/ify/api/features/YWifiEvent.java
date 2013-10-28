package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YWifiEvent extends YEvent {
	public static final int ID = Y.Wifi;

	@Override
	public int getId() {
		return ID;
	}
}
