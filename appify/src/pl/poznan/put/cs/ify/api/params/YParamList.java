package pl.poznan.put.cs.ify.api.params;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.params.YParam.Type;

public class YParamList implements Iterable<Entry<String, YParam>> {
	//TODO: Make it Bundle to make class parcelable easily.
	private HashMap<String, YParam> mParams = new HashMap<String, YParam>();

	public YParam.Type getType(String name) {
		return mParams.get(name).getType();
	}

	/**
	 * Used by receipt to specify types and names of needed params
	 */
	public void add(String name, Type type, Object value) {
		mParams.put(name, new YParam(type, value));
	}

	/**
	 * Generic setter, specialized ones should be used instead
	 */
	@Deprecated
	public void setValue(String name, Object value) {
		mParams.get(name).setValue(value);
	}

	/**
	 * Generic getter, specialized ones should be used instead
	 */
	@Deprecated
	public Object getValue(String name) {
		return mParams.get(name).getValue();
	}

	public void setPosition(String name, YPosition value) {
		if (getType(name) == Type.YPosition)
			mParams.get(name).setValue(value);
	}

	public YPosition getPosition(String name) {
		if (getType(name) == Type.YPosition)
			return (YPosition) mParams.get(name).getValue();
		else
			return null;
	}

	public void setInteger(String name, Integer value) {
		if (getType(name) == Type.Integer)
			mParams.get(name).setValue(value);
	}

	public Integer getInteger(String name) {
		if (getType(name) == Type.Integer)
			return (Integer) mParams.get(name).getValue();
		else
			return null;
	}

	public void setString(String name, String value) {
		if (getType(name) == Type.String)
			mParams.get(name).setValue(value);
	}

	public String getString(String name) {
		if (getType(name) == Type.String)
			return (String) mParams.get(name).getValue();
		else
			return null;
	}
	@Override
	public Iterator<Entry<String, YParam>> iterator() {
		return mParams.entrySet().iterator();
	}
}
