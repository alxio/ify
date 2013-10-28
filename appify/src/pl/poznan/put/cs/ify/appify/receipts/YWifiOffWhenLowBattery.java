package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YBatteryEvent;
import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YWifiOffWhenLowBattery extends YReceipt {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", YParamType.Integer, 90);
	}

	@Override
	public void requestFeatures(YFeatureList feats) {
		feats.add(new YBatteryFeature());
		feats.add(new YWifiFeature());
	}

	@Override
	public String getName() {
		return "WifiOffWhenLowBattery";
	}

	@Override
	public YReceipt newInstance() {
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
