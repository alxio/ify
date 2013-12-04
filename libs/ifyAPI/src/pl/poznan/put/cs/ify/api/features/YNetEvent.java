package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.features.YNetFeature.ResponseType;

public class YNetEvent extends YEvent {
	@Override
	public long getId() {
		return Y.Internet;
	}

	private ResponseType type;
	private Object response;

	public YNetEvent(Object r, ResponseType t) {
		response = r;
		type = t;
	}

	public String asString() {
		if (type == ResponseType.String)
			return (String) response;
		return null;
	}
}
