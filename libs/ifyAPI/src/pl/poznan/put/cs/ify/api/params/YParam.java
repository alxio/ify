package pl.poznan.put.cs.ify.api.params;

import org.json.JSONException;
import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.exceptions.UnimplementedException;
import android.os.Parcel;
import android.os.Parcelable;

public class YParam implements Parcelable {
	public static final String TYPE = "type";
	public static final String VALUE = "value";

	private YParamType mType;
	private Object mValue;

	public YParam(YParamType type, Object value) {
		mType = type;
		setValue(value);
	}

	public YParam(Parcel in) {
		int type = in.readInt();
		mType = YParamType.getByOrdinal(type);
		switch (mType) {
		case Boolean:
			mValue = in.readByte() != 0;
			break;
		case Integer:
			mValue = in.readInt();
			break;
		case String:
			mValue = in.readString();
			break;
		case YPosition:
			throw new UnimplementedException();
			// break;
		default:
			break;
		}
	}

	public YParamType getType() {
		return mType;
	}

	public Object getValue() {
		return mValue;
	}

	void setValue(Object value) {
		mValue = value;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static Object getValueFromString(String s, YParamType type) {
		switch (type) {
		case Boolean:
			return Boolean.parseBoolean(s);
		case String:
			return s;
		case Integer:
			return Integer.parseInt(s);
		default:
			return null;
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mType.ordinal());
		switch (mType) {
		case Boolean:
			dest.writeByte((byte) ((Boolean) mValue ? 0 : 1));
			break;
		case Integer:
			dest.writeInt((Integer) mValue);
			break;
		case String:
			dest.writeString((String) mValue);
			break;
		case YPosition:
			throw new UnimplementedException();
		default:
			break;
		}
	}

	public static final Parcelable.Creator<YParam> CREATOR = new Parcelable.Creator<YParam>() {
		public YParam createFromParcel(Parcel in) {
			return new YParam(in);
		}

		public YParam[] newArray(int size) {
			return new YParam[size];
		}
	};

	public JSONObject toJsonObj() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(TYPE, mType.toString());
		// TODO: Make sure its ok when adding new type
		json.put(VALUE, mValue);
		return json;
	}

	public static YParam fromJsonObj(JSONObject json) {
		try {
			String t = json.getString(TYPE);
			YParamType type = YParamType.fromString(t);
			Object value = json.get(VALUE);
			return new YParam(type, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
