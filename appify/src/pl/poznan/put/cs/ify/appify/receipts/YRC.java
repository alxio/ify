package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.YTextEvent;
import pl.poznan.put.cs.ify.api.features.YTimeFeature;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YRC extends YReceipt {

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YGroupFeature());
		features.add(new YTimeFeature());
		features.add(new YAccelerometerFeature());
		features.add(new YSMSFeature());
	}

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Accelerometer) {
			YGroupFeature feature = (YGroupFeature) mFeatures.get(Y.Group);
			YComm comm = feature.createComm(this, "developers");
			comm.pool();
		}
		if (event.getId() == Y.Text) {
			YGroupFeature feature = (YGroupFeature) mFeatures.get(Y.Group);
			YComm comm = feature.createComm(this, "developers");
			YTextEvent te = (YTextEvent) event;
			comm.sendEvent("BROADCAST", 1, "text", new YParam(YParamType.String, te.getText()));
		}
		if (event.getId() == Y.Group) {
			YGroupEvent ge = (YGroupEvent) event;
			String message = ge.getData().getDataAsString("text");
			String sender = ge.getData().getUserData().getId();
			Log.i("" + "<" + sender + "> " + message);
		}
	}

	@Override
	public String getName() {
		return "YRC";
	}

	@Override
	public YReceipt newInstance() {
		return new YRC();
	}

}
