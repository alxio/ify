package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YAccelerometerEvent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class YAccelerometerFeature extends YFeature {
	public static final long ID = Y.Accelerometer;
	public static final String NAME = "YAccelerometerFeature";

	@Override
	public long getId() {
		return ID;
	}

	private SensorManager mSensorManager;
	private SensorEventListener mSensorListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			sendNotification(new YAccelerometerEvent(event.values));
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@Override
	public void init(IYRecipeHost srv) {
		mSensorManager = (SensorManager) mHost.getContext().getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void uninitialize() {
		mSensorManager.unregisterListener(mSensorListener);
	}

}
