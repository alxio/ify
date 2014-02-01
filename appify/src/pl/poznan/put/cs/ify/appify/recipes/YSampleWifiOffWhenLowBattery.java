package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YBatteryEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Sample recipe turning off WiFi connection when battery is low.
 */
public class YSampleWifiOffWhenLowBattery extends YRecipe {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", YParamType.Integer, 90);
	}

	@Override
	public long requestFeatures() {
		return Y.Battery | Y.Wifi;
	}

	@Override
	public String getName() {
		return "YSampleWifiOffWhenLowBattery";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleWifiOffWhenLowBattery();
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Battery) {
			YBatteryEvent e = (YBatteryEvent) event;
			// print battery percentage to logs
			Log.i(e.getLevel() + "");
			// if battery is low...
			if (e.getLevel() < mParams.getInteger("Level")) {
				// ...disable wifi
				mFeatures.getWifi().disable();
			}
		}
	}
}