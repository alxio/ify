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
package pl.poznan.put.cs.ify.api.types;

/**
 * Class representing 3D vector.
 *
 */
public class YVector {
	private float x;
	private float y;
	private float z;

	/**
	 * Creates vector with given values on X, Y and Z axis.
	 */
	public YVector(float X, float Y, float Z) {
		x = X;
		y = Y;
		z = Z;
	}

	/**
	 * @return new vector representing sum of this and other.
	 */
	public YVector sum(YVector other) {
		return new YVector(other.x + x, other.y + y, other.z + z);
	}

	/**
	 * Adds other vector to this 
	 * @param other vector to add
	 */
	public void add(YVector other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}

	/**
	 * Multiplies x y and z by given float
	 * @return new vector equal to this * f
	 */
	public YVector mult(float f) {
		return new YVector(x * f, y * f, z * f);
	}

	/**
	 * Subtracts other vector from this
	 * @param other
	 */
	public void sub(YVector other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}

	/**
	 * @return squared Euklidesian length of vector
	 */
	public float getLengthSquere() {
		return x * x + y * y + z * z;
	}

	/**
	 * @return Euklidesian length of vector
	 */
	public float getLength() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
}
