package pl.poznan.put.cs.ify.params;


public class YParam {
	private Type mType;
	private Object mValue;

	/**
	 * List of allowed param types TODO: Decide if we box standard types
	 * (Integer into YInteger etc.)
	 */
	public enum Type {
		YPosition, Integer, String
	}

	public YParam(Type type) {
		mType = type;
		mValue = null;
	}

	public Type getType() {
		return mType;
	}

	public Object getValue() {
		return mValue;
	}

	public void setValue(Object value) {
		mValue = value;
	}
}
