package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.types.YVector;

public class YAccelerometerEvent extends YEvent {
	public static final long ID = Y.Accelerometer;
	private float mX;
	private float mY;
	private float mZ;

	@Override
	public long getId() {
		return ID;
	}

	/**
	 * @return Acceleration on X axis.
	 */
	public float getX() {
		return mX;
	}

	/**
	 * @return Acceleration on Y axis.
	 */
	public float getY() {
		return mY;
	}

	/**
	 * @return Acceleration on Z axis.
	 */
	public float getZ() {
		return mZ;
	}

	/**
	 * @return Acceleration as vector.
	 */
	public YVector getVector() {
		return new YVector(mX, mY, mZ);
	}

	/**
	 * @return YAccelerometerEvent with x,y,z values from array.
	 */
	public YAccelerometerEvent(float[] values) {
		mX = values[0];
		mY = values[1];
		mZ = values[2];
	}

}
