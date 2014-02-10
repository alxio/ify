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
package pl.poznan.put.cs.ify.app.ui.server;

import java.util.ArrayList;

import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class GroupsAdapter extends BaseExpandableListAdapter {

	private ArrayList<GroupModel> mGroups;
	private LayoutInflater mInflater;

	public GroupsAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public UserModel getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).getUsers().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 1024 * groupPosition + childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.groups_row_username, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.nick = (TextView) convertView.findViewById(R.id.nick);
			convertView.setTag(holder);
		}
		ViewHolder h = (ViewHolder) convertView.getTag();
		String firstName = getChild(groupPosition, childPosition).firstName;
		String secondName = getChild(groupPosition, childPosition).secondName;
		String nick = getChild(groupPosition, childPosition).nick;
		h.nick.setText(firstName + " " + secondName);
		h.name.setText(nick);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (getGroup(groupPosition) == null
				|| getGroup(groupPosition).getUsers() == null) {
			return 0;
		}
		return getGroup(groupPosition).getUsers().size();
	}

	@Override
	public GroupModel getGroup(int groupPosition) {
		return mGroups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (mGroups == null) {
			return 0;
		}
		return mGroups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.groups_row_group, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}
		ViewHolder h = (ViewHolder) convertView.getTag();
		GroupModel g = getGroup(groupPosition);
		h.name.setText(g.name);
		if (g.amIOwner) {
			h.name.setTypeface(Typeface.DEFAULT_BOLD);
		} else {
			h.name.setTypeface(Typeface.DEFAULT);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	static class ViewHolder {
		TextView nick;
		TextView name;
	}

	public void setGroups(ArrayList<GroupModel> groups) {
		mGroups = groups;
	}

}
