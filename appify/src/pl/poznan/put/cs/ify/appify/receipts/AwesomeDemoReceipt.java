package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;

public class AwesomeDemoReceipt extends YReceipt {

	private boolean alreadySend = false;

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YSMSFeature());
		features.add(new YAccelerometerFeature());
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("SEND_TO", YParam.Type.String, "+48792571392");
		params.add("MIN", YParam.Type.Integer, 10);
	}

	@Override
	public void handleData(YFeature feature, Bundle data) {
		if (feature.getId() != Y.ACCELEROMETER)
			return;
		float x = data.getFloat("X");
		float y = data.getFloat("Y");
		float z = data.getFloat("Z");
		float grall = x * x + y * y + z * z;
		YLog.d("ACC", grall + "");
		int min = mParams.getInteger("MIN");
		if (grall < min && !alreadySend) {
			alreadySend = true;
			YSMSFeature smsFeature = (YSMSFeature) mFeatures.get("YSMSFeature");
			smsFeature.sendSMS(mParams.getString("SEND_TO"), "Ups, upuscilem komorke");
		}

	}

	@Override
	public String getName() {
		return "AwesomeDemoReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new AwesomeDemoReceipt();
	}

}
