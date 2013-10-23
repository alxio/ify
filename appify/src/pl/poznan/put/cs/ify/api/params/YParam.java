package pl.poznan.put.cs.ify.api.params;

import pl.poznan.put.cs.ify.api.exceptions.UnimplementedException;
import android.os.Parcel;
import android.os.Parcelable;

public class YParam implements Parcelable {
	private Type mType;
	private Object mValue;

	/**
	 * List of allowed param types TODO: Decide if we box standard types
	 * (Integer into YInteger etc.)
	 */
	public enum Type {

		YPosition, Integer, String, Boolean;

		static Type getByOrdinal(int ordinal) {
			return Type.values()[ordinal];
		}
	}

	public YParam(Type type, Object value) {
		mType = type;
		setValue(value);
	}

	public YParam(Parcel in) {
		int type = in.readInt();
		mType = Type.getByOrdinal(type);
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

	public Type getType() {
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
			// break;
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
}
