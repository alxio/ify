package pl.poznan.put.cs.ify.app.recipes;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;



/**
 * Sample recipe using YRawPlayer play sounds with frequency based on YAccelerometr
 */
public class YSampleRawPlayerAccelerometer extends YRecipe {
	
	//sound values
	short tab[] = new short[16000];
	
	//used to decide which part of array we should populate with sound and when to play it. 
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
		return "YRawPlayerAccelerometerSample";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleRawPlayerAccelerometer();
	}

	@Override
	public void handleEvent(YEvent event) {
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		
		//extract acceleration and scale it
		int x = (int) Math.max(100 * (10 - e.getX()), 0);
		int y = (int) Math.max(100 * (10 - e.getX()), 0);
		int z = (int) Math.max(100 * (10 - e.getX()), 0);

		float angle = 0;
		
		//add sine waves to sound values base on x, y and z
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

		//when we populated all 10 parts of array play it
		if (++idx == 10) {
			idx = 0;
			getFeatures().getRawPlayer().play(tab, 8000);
		}
	}
}
