package pl.poznan.put.cs.ify.api.group;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YGroupEvent extends YEvent {
	public static final int ID = Y.Group;

	@Override
	public int getId() {
		return ID;
	}
}
