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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Dictionary of YParams based on HashMap. Also contains features mask for usage
 * in YRecipes context.
 */
public class YParamList implements Iterable<Entry<String, YParam>>, Parcelable {
	private HashMap<String, YParam> mParams = new HashMap<String, YParam>();
	private long mFeatures = 0;

	public long getFeatures() {
		return mFeatures;
	}

	public void setFeatures(long features) {
		mFeatures = features;
	}

	/**
	 * @param name
	 *            acts as key to dictionary
	 * @return type of YParam under given key
	 */
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
	 * Used by recipe to specify types and names of needed params
	 */
	public void add(String name, YParamType type, Object value) {
		mParams.put(name, new YParam(type, value));
	}

	/**
	 * Used by recipe to specify types and names of needed params
	 */
	public void add(String name, YParam param) {
		mParams.put(name, param);
	}

	public void setPosition(String name, YPosition value) {
		if (getType(name) == YParamType.Position)
			mParams.get(name).setValue(value);
	}

	/**
	 * @return YParam with given name, or null if it doesn't exist or is other type.
	 */
	public YPosition getPosition(String name) {
		if (getType(name) == YParamType.Position)
			return (YPosition) mParams.get(name).getValue();
		else
			return null;
	}

	public void setInteger(String name, Integer value) {
		if (getType(name) == YParamType.Integer)
			mParams.get(name).setValue(value);
	}
	/**
	 * @return YParam with given name, or null if it doesn't exist or is other type.
	 */
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
	/**
	 * @return YParam with given name, or null if it doesn't exist or is other type.
	 */
	public String getString(String name) {
		if (getType(name) == YParamType.String)
			return (String) mParams.get(name).getValue();
		else
			return null;
	}

	public void setNumber(String name, String value) {
		if (getType(name) == YParamType.Number)
			mParams.get(name).setValue(value);
	}
	/**
	 * @return YParam with given name, or null if it doesn't exist or is other type.
	 */
	public String getNumber(String name) {
		if (getType(name) == YParamType.Number)
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
