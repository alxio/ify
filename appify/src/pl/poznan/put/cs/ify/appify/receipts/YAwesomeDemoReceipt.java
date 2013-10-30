package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YAwesomeDemoReceipt extends YReceipt {

	private boolean alreadySend = false;

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YSMSFeature());
		features.add(new YAccelerometerFeature());
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("SEND_TO", YParamType.String, "+48792571392");
		params.add("MIN", YParamType.Integer, 10);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() != Y.Accelerometer)
			return;
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		float grall = e.getVector().getLengthSquere();
		Log.d(grall + "");
		int min = mParams.getInteger("MIN");
		if (grall < min && !alreadySend) {
			alreadySend = true;
			YSMSFeature smsFeature = (YSMSFeature) mFeatures.get(Y.SMS);
			smsFeature.sendSMS(mParams.getString("SEND_TO"), "Ups, upuscilem komorke");
		}

	}

	@Override
	public String getName() {
		return "AwesomeDemoReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new YAwesomeDemoReceipt();
	}

}
