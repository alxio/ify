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
package pl.poznan.put.cs.ify.app.ui.params.contacts;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.appify.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ContactsAdapter extends BaseExpandableListAdapter {

	private LayoutInflater mInflater;
	private List<Contact> mData = new ArrayList<Contact>();

	public ContactsAdapter(LayoutInflater inflater) {
		mInflater = inflater;
	}

	public void setData(List<Contact> result) {
		mData = result;
	}

	@Override
	public String getChild(int arg0, int arg1) {
		return mData.get(arg0).getPhoneNumbers().get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg0 * mData.size() + arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2,
			View convertView, ViewGroup arg4) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.number_row_number, null);
			ViewHolder holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.contact_number);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.textView.setText(getChild(arg0, arg1));
		return convertView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return mData.get(arg0).getPhoneNumbers().size();
	}

	@Override
	public Contact getGroup(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return mData.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View convertView,
			ViewGroup arg3) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.number_row_name, null);
			ViewHolder holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.contact_name);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.textView.setText(getGroup(arg0).getName());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	class ViewHolder {
		public TextView textView;
	}
}
