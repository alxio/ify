package pl.poznan.put.cs.ify.app.market;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarketInfoAdapter extends BaseAdapter {

	private ArrayList<MarketInfo> mData = new ArrayList<MarketInfo>();
	private Context mContext;

	public MarketInfoAdapter(Context context) {
		mContext = context;
	}

	public void addData(List<MarketInfo> data) {
		mData.addAll(data);
	}

	@Override
	public int getCount() {
		if (mData == null) {
			return 0;
		}
		return mData.size();
	}

	@Override
	public MarketInfo getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.market_info_row, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.market_row_name);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		MarketInfo info = getItem(position);
		holder.name.setText(info.getName());
		return convertView;
	}

	private class ViewHolder {
		public TextView name;
	}
}
