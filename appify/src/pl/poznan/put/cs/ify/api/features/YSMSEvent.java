package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import android.content.Intent;

public class YSMSEvent extends YEvent {
	public static final int ID = Y.SMS;

	public YSMSEvent(Intent intent) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getId() {
		return ID;
	}
}
