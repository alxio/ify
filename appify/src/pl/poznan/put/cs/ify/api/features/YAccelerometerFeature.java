package pl.poznan.put.cs.ify.api.features;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.services.YReceiptsService;

public class YAccelerometerFeature extends YFeature {

	private SensorManager mSensorManager;
	private SensorEventListener mSensorListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			Bundle data = new Bundle();
			data.putFloat("X", event.values[0]);
			data.putFloat("Y", event.values[1]);
			data.putFloat("Z", event.values[2]);
			trySendNotification(data);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@Override
	public String getName() {
		return "YAccelerometerFeature";
	}

	@Override
	public void initialize(Context ctx, YReceiptsService srv) {
		mSensorManager = (SensorManager) ctx
				.getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void uninitialize() {

	}

}
