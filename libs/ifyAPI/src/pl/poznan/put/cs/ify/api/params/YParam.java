package pl.poznan.put.cs.ify.api.params;

import org.json.JSONException;
import org.json.JSONObject;

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
		case Position:
			mValue = in.readParcelable(YPosition.class.getClassLoader());
			break;
		case Group:
			mValue = in.readString();
			break;
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
		case Group:
			return s;
		case Integer:
			return Integer.parseInt(s);
		case Position:
			String[] pos = s.split(";");
			return new YPosition(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Integer.parseInt(pos[2]));
		}
		return null;
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
		case Group:
			dest.writeString((String) mValue);
			break;
		case Position:
			dest.writeParcelable((YPosition) mValue, 0);
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

		if (mType == YParamType.Integer || mType == YParamType.Boolean || mType == YParamType.Group
				|| mType == YParamType.String) {
			json.put(VALUE, mValue);
		} else {
			json.put(VALUE, mValue.toString());
		}

		return json;
	}

	public static YParam fromJsonObj(JSONObject json) {
		try {
			String t = json.getString(TYPE);
			YParamType type = YParamType.fromString(t);
			Object value = null;
			if (type == YParamType.Integer || type == YParamType.Boolean || type == YParamType.Group
					|| type == YParamType.String) {
				value = json.get(VALUE);
			} else {
				String str = json.getString(VALUE);
				value = getValueFromString(str, type);
			}
			return new YParam(type, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static YParam createPosition(YPosition pos) {
		return new YParam(YParamType.Position, pos);
	}
}
