package pl.poznan.put.cs.ify.app.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.app.ui.server.JsonParser;
import pl.poznan.put.cs.ify.app.ui.server.ServerURLBuilder;
import pl.poznan.put.cs.ify.appify.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

public class ManageGroupsFragment extends Fragment {

	private Spinner mInvitesSpinner;
	private Button mAcceptInvitationButton;
	private Button mInviteButton;
	private Spinner mMyGroupsSpinner;
	private String mUsername;
	private EditText mGroupNameEditText;
	private Button mCreateGroupButton;
	private View mNotLoggedInLayout;
	private TextView mInvitationsTextView;
	private TextView mGroupsTextView;
	private TextView mCreateGroupsTextView;
	private View mInvitesLayout;
	private View mMyGroupsLayout;

	private ArrayAdapter<String> mInvitesAdapter;
	private ArrayAdapter<String> mMyGroupsAdapter;
	private String mPassword;
	private EditText mUsernameEditText;
	private OnClickListener mOnAcceptClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String group = (String) mInvitesSpinner.getSelectedItem();
			if (!group.isEmpty()) {
				String url = new ServerURLBuilder(getActivity())
						.postAcceptInvite(mUsername, mPassword, group);
				StringRequest r = new StringRequest(Method.POST, url,
						new Listener<String>() {

							@Override
							public void onResponse(String arg0) {
								updateInvitesAdapter();
								updateGroupsAdapter();
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {

							}
						});
				QueueSingleton.getInstance(getActivity()).add(r);
			}
		}
	};
	private OnClickListener mOnInviteClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String username = mUsernameEditText.getText().toString();
			String groupName = (String) mMyGroupsSpinner.getSelectedItem();
			String url = new ServerURLBuilder(getActivity()).sendInvite(
					mUsername, mPassword, groupName, username);
			StringRequest r = new StringRequest(Method.GET, url,
					new Listener<String>() {

						@Override
						public void onResponse(String arg0) {
							// updateGroupsAdapter();
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError arg0) {
							Log.d("ERROR", arg0.toString());
						}
					});
			QueueSingleton.getInstance(getActivity()).add(r);
		}
	};
	private OnClickListener mOnCreateGroupClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String groupName = mGroupNameEditText.getText().toString();
			String url = new ServerURLBuilder(getActivity()).createGroup(
					mUsername, mPassword, groupName);
			StringRequest r = new StringRequest(Method.POST, url,
					new Listener<String>() {

						@Override
						public void onResponse(String arg0) {
							updateGroupsAdapter();
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError arg0) {
						}
					});
			QueueSingleton.getInstance(getActivity()).add(r);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.manage_groups, null);
		mNotLoggedInLayout = v.findViewById(R.id.not_logged);
		mInvitesSpinner = (Spinner) v.findViewById(R.id.manage_invites_spinner);
		mAcceptInvitationButton = (Button) v
				.findViewById(R.id.manage_invites_sendInvite);
		mMyGroupsSpinner = (Spinner) v
				.findViewById(R.id.manage_mygroups_spinner);
		mUsernameEditText = (EditText) v
				.findViewById(R.id.manage_mygroups_username);
		mInviteButton = (Button) v.findViewById(R.id.manage_mygroups_invite);
		mGroupNameEditText = (EditText) v
				.findViewById(R.id.manage_creategroup_tv_name);
		mCreateGroupButton = (Button) v
				.findViewById(R.id.manage_creategroup_button);
		mCreateGroupButton.setOnClickListener(mOnCreateGroupClicked);
		mInviteButton.setOnClickListener(mOnInviteClicked);
		mAcceptInvitationButton.setOnClickListener(mOnAcceptClicked);
		mInvitesAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item);
		mInvitesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mMyGroupsAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item);
		mMyGroupsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mInvitesSpinner.setAdapter(mInvitesAdapter);
		mMyGroupsSpinner.setAdapter(mMyGroupsAdapter);

		mInvitationsTextView = (TextView) v
				.findViewById(R.id.manage_tv_invites);
		mGroupsTextView = (TextView) v.findViewById(R.id.manage_mygroups_tv);
		mGroupsTextView = (TextView) v.findViewById(R.id.manage_creategroup_tv);

		mInvitesLayout = v.findViewById(R.id.manage_invites_layout);
		mMyGroupsLayout = v.findViewById(R.id.manage_mygroups_invite);
		return v;
	}

	public void updateInvitesAdapter() {
		ServerURLBuilder b = new ServerURLBuilder(getActivity());
		String url = b.getMyInvites(mUsername, mPassword);
		Listener<JSONArray> listener = new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray arg0) {
				try {
					JsonParser parser = new JsonParser();
					ArrayList<String> invitations = parser
							.parseGetInvitations(arg0);
					mInvitesAdapter.clear();
					mInvitesAdapter.addAll(invitations);
					mInvitesAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
				}
			}
		};
		ErrorListener errorListener = getErrorListener("There was a problem with downloading invitations. Do you want to retry?");
		JsonArrayRequest r = new JsonArrayRequest(url, listener, errorListener);
		QueueSingleton.getInstance(getActivity()).add(r);
	}

	private ErrorListener getErrorListener(String string) {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setTitle("Connection error");
				builder.setMessage("Error occured during downloading invitations. Do you want to retry?");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								updateInvitesAdapter();
							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
			}
		};
	}

	public void updateGroupsAdapter() {
		ServerURLBuilder b = new ServerURLBuilder(getActivity());
		String url = b.getMyGroups(mUsername, mPassword);
		Listener<JSONArray> listener = new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray arg0) {
				try {
					JsonParser parser = new JsonParser();
					ArrayList<String> invitations = parser.parseGetMyGroups(
							arg0, mUsername);
					mMyGroupsAdapter.clear();
					mMyGroupsAdapter.addAll(invitations);
					mMyGroupsAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
				}
			}
		};
		ErrorListener errorListener = getErrorListener("There was a problem with downloading your groups. Do you want to retry?");
		JsonArrayRequest r = new JsonArrayRequest(url, listener, errorListener);
		QueueSingleton.getInstance(getActivity()).add(r);
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
			updateGroupsAdapter();
			updateInvitesAdapter();
		}
	}
}
