package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YCallsEvent extends YEvent{

	public final String state;
	public final String incomingNumber;

	public YCallsEvent(String string, String incomingNumber) {
		this.state = string;
		this.incomingNumber = incomingNumber;
	}

	@Override
	public long getId() {
		return Y.Calls;
	}
}
