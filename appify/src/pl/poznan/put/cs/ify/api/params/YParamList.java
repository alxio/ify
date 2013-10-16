package pl.poznan.put.cs.ify.api.params;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.params.YParam.Type;
import android.os.Parcel;
import android.os.Parcelable;

public class YParamList implements Iterable<Entry<String, YParam>>, Parcelable {
	// TODO: Make it Bundle to make class parcelable easily.
	private HashMap<String, YParam> mParams = new HashMap<String, YParam>();

	public YParam.Type getType(String name) {
		return mParams.get(name).getType();
	}

	public YParamList() {

	}

	/**
	 * Used by receipt to specify types and names of needed params
	 */
	public void add(String name, Type type, Object value) {
		mParams.put(name, new YParam(type, value));
	}

	/**
	 * Used by receipt to specify types and names of needed params
	 */
	public void add(String name, YParam param) {
		mParams.put(name, param);
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		for (Entry<String, YParam> entry : this) {
			dest.writeString(entry.getKey());
			dest.writeParcelable(entry.getValue(), 0);
		}
	}

	public YParamList(Parcel in) {
		while (in.dataPosition() < in.dataSize()) {
			String name = in.readString();
			YParam param = in.readParcelable(YParam.class.getClassLoader());
			mParams.put(name, param);
		}
	}

	public static final Parcelable.Creator<YParamList> CREATOR = new Parcelable.Creator<YParamList>() {
		public YParamList createFromParcel(Parcel in) {
			return new YParamList(in);
		}

		public YParamList[] newArray(int size) {
			return new YParamList[size];
		}
	};
}
