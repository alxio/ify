package pl.poznan.put.cs.ify.app.fragments;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.app.ui.server.JsonParser;
import pl.poznan.put.cs.ify.app.ui.server.ServerURLBuilder;
import pl.poznan.put.cs.ify.appify.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyGroupsFragment extends Fragment {

	private ArrayList<String> mGroups = new ArrayList<String>();
	private String mUsername;
	private View mNotLoggedInLayout;
	private String mPassword;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.my_groups, null);
		mNotLoggedInLayout = v.findViewById(R.id.not_logged);
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
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray source) {
						JsonParser parser = new JsonParser();
						try {
							ArrayList<String> groups = parser
									.parseGetGroups(source);
							if (groups != null) {
								mGroups = groups;
								getGroupsMembers();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					private void getGroupsMembers() {
						// TODO Auto-generated method stub
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub

					}
				});
	}
}
