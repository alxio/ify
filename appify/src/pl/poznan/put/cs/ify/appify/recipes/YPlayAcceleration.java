package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YRawPlayerFeature;
import pl.poznan.put.cs.ify.api.features.events.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YPlayAcceleration extends YRecipe {

	short tab[] = new short[16000];
	int idx = 0;

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public long requestFeatures() {
		return Y.Accelerometer | Y.RawPlayer;
	}

	@Override
	public String getName() {
		return "YPlayAcceleration";
	}

	@Override
	public YRecipe newInstance() {
		return new YPlayAcceleration();
	}

	@Override
	public void handleEvent(YEvent event) {
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		Log.i("Event");

		int x = (int) Math.max(100 * (10 - e.getX()), 0);
		int y = (int) Math.max(100 * (10 - e.getX()), 0);
		int z = (int) Math.max(100 * (10 - e.getX()), 0);

		float angle = 0;
		for (int i = idx * tab.length / 10; i < (idx + 1) * tab.length / 10; i++) {
			float angular_frequency = (float) (2 * Math.PI) * x / 8000;
			tab[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}
		angle = 0;
		for (int i = idx * tab.length / 10; i < (idx + 1) * tab.length / 10; i++) {
			float angular_frequency = (float) (2 * Math.PI) * y / 4000;
			tab[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}
		angle = 0;
		for (int i = idx * tab.length / 10; i < (idx + 1) * tab.length / 10; i++) {
			float angular_frequency = (float) (2 * Math.PI) * z / 16000;
			tab[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}

		if (++idx == 10) {
			Log.i("Playing");
			idx = 0;
			((YRawPlayerFeature) mFeatures.get(Y.RawPlayer)).play(tab, 8000);
		}
	}
}
