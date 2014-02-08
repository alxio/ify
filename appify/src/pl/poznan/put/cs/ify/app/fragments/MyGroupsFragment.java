package pl.poznan.put.cs.ify.app.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.app.ui.server.GroupModel;
import pl.poznan.put.cs.ify.app.ui.server.GroupsAdapter;
import pl.poznan.put.cs.ify.app.ui.server.JsonParser;
import pl.poznan.put.cs.ify.app.ui.server.ServerURLBuilder;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class MyGroupsFragment extends Fragment {

	private ArrayList<GroupModel> mGroups = new ArrayList<GroupModel>();
	private GroupsAdapter mGroupsAdapter;
	private HashMap<String, ArrayList<String>> mGroupUsersMap = new HashMap<String, ArrayList<String>>();
	private String mUsername;
	private View mNotLoggedInLayout;
	private String mPassword;
	private ErrorListener mRequestGroupsErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			mProgress--;
			if (mProgress == 0) {
				mProgressLayout.setVisibility(View.GONE);
			}
			mErrorLayout.setVisibility(View.GONE);
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
					getGroupsMembers();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	};
	protected int mProgress;
	private Listener<JSONArray> mGetUsersListener = new Listener<JSONArray>() {

		@Override
		public void onResponse(JSONArray arg0) {
			mProgress--;
			if (mProgress == 0) {
				mProgressLayout.setVisibility(View.GONE);
			}
		}
	};
	private ErrorListener mGetUsersError = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			mProgress--;
			if (mProgress == 0) {
				mProgressLayout.setVisibility(View.GONE);
			}
			mErrorLayout.setVisibility(View.VISIBLE);
		}
	};
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

			}
		});
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
		JsonArrayRequest r = new JsonArrayRequest(getGroupsUrl,
				mRequestGroupsListener, mRequestGroupsErrorListener);
		QueueSingleton.getInstance(getActivity()).add(r);
		mProgress++;
		mProgressLayout.setVisibility(View.VISIBLE);
	}

	protected void getGroupsMembers() {
		for (GroupModel groupModel : mGroups) {
			String groupName = groupModel.name;
			ServerURLBuilder b = new ServerURLBuilder(getActivity());
			String url = b.getUserInGroup(mUsername, mPassword, groupName);
			JsonArrayRequest r = new JsonArrayRequest(url, mGetUsersListener,
					mGetUsersError);
			mProgress++;
			mProgressLayout.setVisibility(View.VISIBLE);
			QueueSingleton.getInstance(getActivity()).add(r);
		}
	}
}
