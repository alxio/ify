package cs.put.poznan.pl.summoner.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import cs.put.poznan.pl.summoner.R;
import cs.put.poznan.pl.summoner.model.UserInfo;

public class UsersAdapter extends BaseAdapter implements ListAdapter {

	private LayoutInflater mInflater;
	private List<UserInfo> mData;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm dd-MM-yyyy", Locale.getDefault());

	public UsersAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (mData == null) {
			return 0;
		}
		return mData.size();
	}

	@Override
	public UserInfo getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_row, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.updatedTimestamp = (TextView) convertView
					.findViewById(R.id.date);
			holder.message = (TextView) convertView.findViewById(R.id.message);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		UserInfo u = getItem(pos);
		holder.name.setText(u.getName());
		if (u.getUpdatedAtPos() != null) {
			holder.updatedTimestamp.setText(dateFormat.format(new Date(u
					.getUpdatedAtMsg())));
		}
		if (u.getMessage() != null) {
			holder.message.setText(u.getMessage());
		}
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView updatedTimestamp;
		TextView message;
	}

	public void setData(List<UserInfo> data) {
		mData = data;
	}
}
