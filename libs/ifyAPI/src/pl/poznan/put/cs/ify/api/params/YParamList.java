package pl.poznan.put.cs.ify.api.params;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.os.Parcel;
import android.os.Parcelable;

public class YParamList implements Iterable<Entry<String, YParam>>, Parcelable {
	private HashMap<String, YParam> mParams = new HashMap<String, YParam>();
	private long mFeatures = 0;

	public long getFeatures() {
		return mFeatures;
	}

	public void setFeatures(long features) {
		mFeatures = features;
	}

	public YParamType getType(String name) {
		return mParams.get(name).getType();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, YParam> e : mParams.entrySet()) {
			sb.append("{");
			sb.append(e.getKey());
			sb.append(",");
			sb.append(e.getValue().toString());
			sb.append("}");
		}
		return sb.toString();
	}

	public YParamList() {

	}

	/**
	 * Used by receipt to specify types and names of needed params
	 */
	public void add(String name, YParamType type, Object value) {
		mParams.put(name, new YParam(type, value));
	}

	/**
	 * Used by receipt to specify types and names of needed params
	 */
	public void add(String name, YParam param) {
		mParams.put(name, param);
	}

	public void setPosition(String name, YPosition value) {
		if (getType(name) == YParamType.YPosition)
			mParams.get(name).setValue(value);
	}

	public YPosition getPosition(String name) {
		if (getType(name) == YParamType.YPosition)
			return (YPosition) mParams.get(name).getValue();
		else
			return null;
	}

	public void setInteger(String name, Integer value) {
		if (getType(name) == YParamType.Integer)
			mParams.get(name).setValue(value);
	}

	public Integer getInteger(String name) {
		if (getType(name) == YParamType.Integer)
			return (Integer) mParams.get(name).getValue();
		else
			return null;
	}

	public void setString(String name, String value) {
		if (getType(name) == YParamType.String)
			mParams.get(name).setValue(value);
	}

	public String getString(String name) {
		if (getType(name) == YParamType.String)
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
		dest.writeLong(mFeatures);
		dest.writeInt(mParams.size());
		for (Entry<String, YParam> entry : this) {
			dest.writeString(entry.getKey());
			dest.writeParcelable(entry.getValue(), 0);
		}
	}

	public YParamList(Parcel in) {
		mFeatures = in.readLong();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
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
