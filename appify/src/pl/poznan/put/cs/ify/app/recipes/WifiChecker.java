package pl.poznan.put.cs.ify.app.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent.WiFi_EventType;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class WifiChecker extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Wifi | Y.Notification;
	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		if (event.getId() == Y.Wifi) {
			YWifiEvent we = (YWifiEvent) event;
			Log.i(we.getEventType().name());
			if (we.getEventType() == WiFi_EventType.DISCONNECTED) {
				getFeatures().getNotification().createNotification(
						"connection lost", this);
			}
		}
	}

	@Override
	public String getName() {
		return "WifiChecker";
	}

	@Override
	public YRecipe newInstance() {
		return new WifiChecker();
	}

}
