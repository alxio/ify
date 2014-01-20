package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YBatteryEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YWifiOffWhenLowBattery extends YRecipe {
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
		return "WifiOffWhenLowBattery";
	}

	@Override
	public YRecipe newInstance() {
		return new YWifiOffWhenLowBattery();
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Battery) {
			YBatteryEvent e = (YBatteryEvent) event;
			Log.i(e.getLevel() + "");
			if (e.getLevel() < mParams.getInteger("Level")) {
				mFeatures.getWifi().disable();
			}
		}
	}
}
