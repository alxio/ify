package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YBatteryEvent extends YEvent {
	public static final long ID = Y.Battery;
	private int mLevel;

	@Override
	public long getId() {
		return ID;
	}

	/**
	 * @return percentage of battery level.
	 */
	public int getLevel() {
		return mLevel;
	}

	public YBatteryEvent(int level) {
		mLevel = level;
	}
}
