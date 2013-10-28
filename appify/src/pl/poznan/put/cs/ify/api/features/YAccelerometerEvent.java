package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.types.YVector;

public class YAccelerometerEvent extends YEvent {
	public static final int ID = Y.Accelerometer;
	private float mX;
	private float mY;
	private float mZ;

	@Override
	public int getId() {
		return ID;
	}

	public float getX() {
		return mX;
	}

	public float getY() {
		return mX;
	}

	public float getZ() {
		return mX;
	}

	public YVector getVector() {
		return new YVector(mX, mY, mZ);
	}

	public YAccelerometerEvent(float[] values) {
		mX = values[0];
		mY = values[1];
		mZ = values[2];
	}

}
