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
package pl.poznan.put.cs.ify.app.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.app.ui.server.GroupModel;
import pl.poznan.put.cs.ify.app.ui.server.GroupsAdapter;
import pl.poznan.put.cs.ify.app.ui.server.JsonParser;
import pl.poznan.put.cs.ify.app.ui.server.ServerURLBuilder;
import pl.poznan.put.cs.ify.app.ui.server.UserModel;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class MyGroupsFragment extends Fragment {

	private GroupsAdapter mGroupsAdapter;
	private ArrayList<GroupModel> mGroups;
	private String mUsername;
	private View mNotLoggedInLayout;
	private String mPassword;
	private ErrorListener mErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			mProgress--;
			if (mProgress == 0) {
				mProgressLayout.setVisibility(View.GONE);
			}
			mErrorLayout.setVisibility(View.VISIBLE);
		}
	};
	private Listener<JSONArray> mRequestGroupsListener = new Listener<JSONArray>() {

		@Override
		public void onResponse(JSONArray source) {
			JsonParser parser = new JsonParser();
			mProgress--;
			if (mProgress == 0) {
				mProgressLayout.setVisibility(View.GONE);
			}
			try {
				ArrayList<GroupModel> groups = parser.parseGetGroups(source,
						mUsername);
				if (groups != null) {
					mGroups = groups;
					mGroupsAdapter.setGroups(groups);
					mGroupsAdapter.notifyDataSetChanged();
					getGroupsMembers();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	};
	protected int mProgress;

	private View mErrorLayout;
	private View mProgressLayout;
	private ExpandableListView mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.my_groups, null);
		mNotLoggedInLayout = v.findViewById(R.id.not_logged);
		mProgressLayout = v.findViewById(R.id.mygroups_progress);
		mErrorLayout = v.findViewById(R.id.mygroups_error);
		mList = (ExpandableListView) v.findViewById(R.id.mygroups_list);

		mErrorLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mGroups != null) {
					mGroups.clear();
				}
				mGroupsAdapter.notifyDataSetChanged();
				getGroups();
			}
		});

		mGroupsAdapter = new GroupsAdapter(getActivity());
		mList.setAdapter(mGroupsAdapter);

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		mUsername = prefs.getString(PreferencesProvider.KEY_USERNAME);
		if (mUsername.equals(PreferencesProvider.DEFAULT_STRING)) {
			mNotLoggedInLayout.setVisibility(View.VISIBLE);
		} else {
			mNotLoggedInLayout.setVisibility(View.GONE);
			mPassword = prefs.getString(PreferencesProvider.KEY_HASH);
			getGroups();
		}
	}

	private void getGroups() {
		String getGroupsUrl = new ServerURLBuilder(getActivity()).getMyGroups(
				mUsername, mPassword);
		mErrorLayout.setVisibility(View.GONE);
		JsonArrayRequest r = new JsonArrayRequest(getGroupsUrl,
				mRequestGroupsListener, mErrorListener);
		QueueSingleton.getInstance(getActivity()).add(r);
		mProgress++;
		mProgressLayout.setVisibility(View.VISIBLE);
	}

	protected void getGroupsMembers() {
		for (GroupModel groupModel : mGroups) {
			if (getActivity() == null) {
				return;
			}
			final String groupName = groupModel.name;
			ServerURLBuilder b = new ServerURLBuilder(getActivity());
			String url = b.getUserInGroup(mUsername, mPassword, groupName);
			Listener<JSONArray> getUsersListener = new Listener<JSONArray>() {

				@Override
				public void onResponse(JSONArray source) {
					JsonParser parser = new JsonParser();
					mProgress--;
					if (mProgress == 0) {
						mProgressLayout.setVisibility(View.GONE);
					}
					try {
						ArrayList<UserModel> users = parser
								.parseGetUsers(source);
						if (users != null) {
							for (GroupModel group : mGroups) {
								if (group.name.equals(groupName)) {
									group.setUsers(users);
									mGroupsAdapter.notifyDataSetChanged();
									return;
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			};
			JsonArrayRequest r = new JsonArrayRequest(url, getUsersListener,
					mErrorListener);
			mProgress++;
			mProgressLayout.setVisibility(View.VISIBLE);
			QueueSingleton.getInstance(getActivity()).add(r);
		}
	}
}
