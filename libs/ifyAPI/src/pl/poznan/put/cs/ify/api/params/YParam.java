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
			mValue = in.readString();
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
			return new YPosition(Double.parseDouble(pos[0]),
					Double.parseDouble(pos[1]), Integer.parseInt(pos[2]));
		default:
			return s;
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
		case Group:
			dest.writeString((String) mValue);
			break;
		case Position:
			dest.writeParcelable((YPosition) mValue, 0);
		default:
			dest.writeString((String) mValue);
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

		if (mType == YParamType.Integer || mType == YParamType.Boolean
				|| mType == YParamType.Group || mType == YParamType.String) {
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
			if (type == YParamType.Integer || type == YParamType.Boolean
					|| type == YParamType.Group || type == YParamType.String) {
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

	public static YParam createInteger(Integer i) {
		return new YParam(YParamType.Integer, i);
	}

	public static YParam createPosition(YPosition pos) {
		return new YParam(YParamType.Position, pos);
	}

	public static YParam createString(String s) {
		return new YParam(YParamType.String, s);
	}

	public static YParam createBoolean(Boolean b) {
		return new YParam(YParamType.Boolean, b);
	}

	public static YParam createGroup(String s) {
		return new YParam(YParamType.Group, s);
	}

	public static YParam createNumber(String s) {
		return new YParam(YParamType.Number, s);
	}

}
