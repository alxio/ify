package pl.poznan.put.cs.ify.app.ui.server;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class GroupsAdapter extends BaseExpandableListAdapter {

	private ArrayList<GroupModel> mGroups;
	private HashMap<String, ArrayList<String>> mGroupsMap;
	private LayoutInflater mInflater;

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 1024 * groupPosition + childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getGroup(groupPosition).size();
	}

	@Override
	public ArrayList<String> getGroup(int groupPosition) {
		String groupName = mGroups.get(groupPosition).name;
		return mGroupsMap.get(groupName);
	}

	@Override
	public int getGroupCount() {
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
		}
		return null;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	class ViewHolder {
		private TextView name;
	}

}
