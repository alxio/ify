package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.exceptions.UninitializedException;
import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;
import pl.poznan.put.cs.ify.api.params.YParam.Type;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;

public class WifiOffWhenLowBattery extends YReceipt {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", Type.Integer, 90);
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
		return new WifiOffWhenLowBattery();
	}

	@Override
	public void handleData(YFeature feature, Bundle data) throws UninitializedException {
		if (feature.getName().equals("YBatteryFeature")
				&& ((YBatteryFeature) feature).getLevel() < mParams.getInteger("Level")) {
			mFeatures.getWifi().disable();
		}
	}
}
