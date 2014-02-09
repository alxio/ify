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

public class YVector {
	float x;
	float y;
	float z;

	public YVector(float pX, float pY, float pZ) {
		x = pX;
		y = pY;
		z = pZ;
	}

	public YVector sum(YVector p) {
		return new YVector(p.x + x, p.y + y, p.z + z);
	}

	public void add(YVector p) {
		x += p.x;
		y += p.y;
		z += p.z;
	}

	public YVector mult(float f) {
		return new YVector(x * f, y * f, z * f);
	}

	public void sub(YVector p) {
		x -= p.x;
		y -= p.y;
		z -= p.z;
	}

	public float getLengthSquere() {
		return x * x + y * y + z * z;
	}

	public float getLength() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
}
