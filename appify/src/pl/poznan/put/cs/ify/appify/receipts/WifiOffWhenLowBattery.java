package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.core.UninitializedException;
import pl.poznan.put.cs.ify.core.YFeatureList;
import pl.poznan.put.cs.ify.core.YReceipt;
import pl.poznan.put.cs.ify.core.YTrigger;
import pl.poznan.put.cs.ify.features.YBatteryFeature;
import pl.poznan.put.cs.ify.features.YWifi;
import pl.poznan.put.cs.ify.features.triggers.YBatteryTrigger;
import pl.poznan.put.cs.ify.params.YParam.Type;
import pl.poznan.put.cs.ify.params.YParamList;

public class WifiOffWhenLowBattery extends YReceipt {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", Type.Integer);
	}
	@Override
	public void requestFeatures(YFeatureList feats) {
		feats.add(new YBatteryFeature());
		feats.add(new YWifi());
	}

	@Override
	public void handleTrigger(YTrigger trigger) throws UninitializedException {
		if(((YBatteryTrigger)trigger).getLevel() <= mParams.getInteger("Level"))
			mFeatures.getWifi().disable();
	}
	@Override
	public String getName() {
		return "WifiOffWhenLowBattery";
	}
}
