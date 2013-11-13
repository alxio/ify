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
		x *= f;
		y *= f;
		z *= f;
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
