import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YSoundFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YBadumReceipt extends YReceipt {

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YAccelerometerFeature());
		features.add(new YSoundFeature());
	}

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() != Y.Accelerometer)
			return;
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		float grall = e.getVector().getLengthSquere();
		Log.d(grall + "");
		int min = mParams.getInteger("ACTIVATE_GREATER");
		if (grall > 1000) {
			YSoundFeature yFeature = (YSoundFeature) mFeatures
					.get("YSoundFeature");
			yFeature.playSound();
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YReceipt newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
