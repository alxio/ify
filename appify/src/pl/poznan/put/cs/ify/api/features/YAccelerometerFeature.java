package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class YAccelerometerFeature extends YFeature {
	public static final int ID = Y.ACCELEROMETER;
	public static final String NAME = "YAccelerometerFeature";

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getName() {
		return NAME;
	}

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
	public void init(YReceiptsService srv) {
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void uninitialize() {
		mSensorManager.unregisterListener(mSensorListener);
	}

}
