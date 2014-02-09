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
package pl.poznan.put.cs.ify.app.market;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.market_info_row, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.market_row_name);
			holder.skull = (ImageView) convertView.findViewById(R.id.market_row_skull);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		MarketInfo info = getItem(position);
		holder.name.setText(info.getName());
		holder.skull.setVisibility(info.isSafe() ? View.INVISIBLE : View.VISIBLE);
		return convertView;
	}

	private class ViewHolder {
		public TextView name;
		public ImageView skull;
	}
}
