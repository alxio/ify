package pl.poznan.put.cs.ify.appify.receipts;

import android.content.pm.FeatureInfo;
import android.os.Bundle;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.exceptions.UninitializedException;
import pl.poznan.put.cs.ify.api.features.YPositionHelper;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.api.features.YWifi;
import pl.poznan.put.cs.ify.api.params.YParam.Type;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class WifiInJob extends YReceipt {
	@Override
	public void requestParams(YParamList params) {
		params.add("WorkPosition", Type.YPosition, null);
	}

	@Override
	public void requestFeatures(YFeatureList feats) {
		feats.add(new YWifi());
	}

	@Override
	public void initialize(YParamList params, YFeatureList features) {

		// TODO: Add trigger
	}

	@Override
	public String getName() {
		return "WifiInJob";
	}

	@Override
	public YReceipt newInstance() {
		return new WifiInJob();
	}

	@Override
	public void handleData(YFeature feature, Bundle data)
			throws UninitializedException {
		// TODO: maybe change when we will have GPS
		double dist = YPositionHelper.getDistance(mParams
				.getPosition("WorkPosition"));

		// don't change between 10 and 11 to avoid glimmering

		if (dist < 10) {
			// example of specific getter
			mFeatures.getWifi().enable();
		}
		if (dist > 11) {
			// example of usage with casting
			((YWifi) mFeatures.get("YWifi")).disable();
		}
	}
}
