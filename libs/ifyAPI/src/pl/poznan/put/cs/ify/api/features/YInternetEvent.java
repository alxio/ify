package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.features.YInternetFeature.ResponseType;

public class YInternetEvent extends YEvent {
	@Override
	public long getId() {
		return Y.Internet;
	}

	private ResponseType type;
	private Object response;

	public YInternetEvent(Object r, ResponseType t) {
		response = r;
		type = t;
	}

	/**
	 * @return Response if it's String or null otherwise.
	 */
	public String asString() {
		if (type == ResponseType.String)
			return (String) response;
		return null;
	}
}
