package pl.poznan.put.cs.ify.api.group;

import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YGroupEvent extends YEvent {
	public static final int ID = Y.Group;
	private YCommData mData;

	@Override
	public int getId() {
		return ID;
	}

	public YGroupEvent(YCommData data) {
		mData = data;
	}

	public YCommData getData() {
		return mData;
	}
}
