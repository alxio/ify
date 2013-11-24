package pl.poznan.put.cs.ify.api.group;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YGroupEvent extends YEvent {
	public static final long ID = Y.Group;
	private YCommData mData;

	@Override
	public long getId() {
		return ID;
	}

	public YGroupEvent(YCommData data) {
		mData = data;
	}

	public YCommData getData() {
		return mData;
	}
}
