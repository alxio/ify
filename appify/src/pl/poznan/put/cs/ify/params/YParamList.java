package pl.poznan.put.cs.ify.params;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pl.poznan.put.cs.ify.params.YParam.Type;

public class YParamList {
	private HashMap<String, YParam> mParams;

	public YParam.Type getType(String name) {
		return mParams.get(name).getType();
	}

	/**
	 * Used by receipt to specify types and names of needed params
	 */
	public void add(String name, YParam.Type type) {
		mParams.put(name, new YParam(type));
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
	
	
	Iterator<Map.Entry<String,YParam>> it = mParams.entrySet().iterator();
}
