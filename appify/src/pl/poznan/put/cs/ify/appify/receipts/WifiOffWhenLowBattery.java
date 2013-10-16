package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.api.exceptions.UninitializedException;
import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.api.features.YWifi;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParam.Type;
import pl.poznan.put.cs.ify.api.triggers.YBatteryTrigger;

public class WifiOffWhenLowBattery extends YReceipt {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", Type.Integer, 90);
	}

	@Override
	public void requestFeatures(YFeatureList feats) {
		feats.add(new YBatteryFeature());
		feats.add(new YWifi());
	}

	@Override
	public void handleTrigger(YTrigger trigger) throws UninitializedException {
		if (((YBatteryTrigger) trigger).getLevel() <= mParams
				.getInteger("Level"))
			mFeatures.getWifi().disable();
	}

	@Override
	public String getName() {
		return "WifiOffWhenLowBattery";
	}

	@Override
	public YReceipt newInstance() {
		return new WifiOffWhenLowBattery();
	}
}
