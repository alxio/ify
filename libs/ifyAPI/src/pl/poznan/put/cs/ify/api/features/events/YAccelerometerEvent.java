/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.types.YVector;

/**
 * Represents reading from accelerometer.
 *
 */
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
