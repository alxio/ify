package pl.poznan.put.cs.ify.app.market;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pl.poznan.put.cs.ify.appify.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {

	private ArrayList<MarketComment> mData;
	private LayoutInflater mInflater;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm dd-MM-yyyy", Locale.getDefault());

	public CommentsAdapter(ArrayList<MarketComment> data,
			LayoutInflater inflater) {
		super();
		mData = data;
		mInflater = inflater;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public MarketComment getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.comment_row, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		MarketComment comment = getItem(position);
		holder.name.setText(comment.getName());
		holder.comment.setText(comment.getContent());
		holder.date.setText(dateFormat.format(comment.getTimestamp()));
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView date;
		TextView comment;
	}

}
