package pl.poznan.put.cs.ify.api.types;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import pl.poznan.put.cs.ify.api.log.YLogEntry;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class YLogEntryList extends ArrayList<YLogEntry> implements YList<YLogEntry> {
	public YLogEntryList() {
		super();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.size());
		for (YLogEntry entry : this) {
			dest.writeParcelable(entry, 0);
		}
	}

	public YLogEntryList(Parcel in) {
		int size = in.readInt();
		Log.d("SIZE", "" + size);
		for (int i = 0; i < size; i++) {
			YLogEntry entry = in.readParcelable(YLogEntry.class.getClassLoader());
			add(entry);
		}
	}

	public static final Parcelable.Creator<YLogEntryList> CREATOR = new Parcelable.Creator<YLogEntryList>() {
		public YLogEntryList createFromParcel(Parcel in) {
			return new YLogEntryList(in);
		}

		public YLogEntryList[] newArray(int size) {
			return new YLogEntryList[size];
		}
	};

	public String toHTML() {
		StringBuilder sb = new StringBuilder();
		for (YLogEntry l : this) {
			sb.append(l.toHtml());
			sb.append("<br>");
		}
		return sb.toString();
	}
}
