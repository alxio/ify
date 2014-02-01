package pl.poznan.put.cs.ify.app.fragments;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
	View mLoginLayout;
	View mLogoutLayout;
	TextView mError;
	TextView mUsername;
	EditText mLogin;
	EditText mPassword;
	private boolean isAttached;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_login, null);
		initFields(v);
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		String username = prefs.getString(PreferencesProvider.KEY_USERNAME);
		if (username.equals(PreferencesProvider.DEFAULT_STRING)) {
			initLoginLayout();
		} else {
			initLogoutLayout(username);
		}
		return v;
	}

	private void initFields(View v) {
		mLoginLayout = v.findViewById(R.id.layLogin);
		mLogoutLayout = v.findViewById(R.id.layLogout);
		mUsername = (TextView) v.findViewById(R.id.username);
		mLogin = (EditText) v.findViewById(R.id.login);
		mPassword = (EditText) v.findViewById(R.id.password);
		mError = (TextView) v.findViewById(R.id.error);
	}

	public void initLogoutLayout(String username) {
		mLoginLayout.setVisibility(View.GONE);
		mLogoutLayout.setVisibility(View.VISIBLE);
		mUsername.setText(username);
	}

	public void initLoginLayout() {
		mLoginLayout.setVisibility(View.VISIBLE);
		mLogoutLayout.setVisibility(View.GONE);
		mError.setVisibility(View.INVISIBLE);
	}

	public void login(View v) {
		if (TextUtils.isEmpty(mLogin.getText())) {
			mError.setText("Enter login");
			mError.setVisibility(View.VISIBLE);
		} else {
			mError.setText("Wait...");
			mError.setVisibility(View.VISIBLE);
			((MainActivity) getActivity()).login(mLogin.getText().toString(),
					mPassword.getText().toString());
		}
	}

	public void logout(View v) {
		((MainActivity) getActivity()).logout();

	}

	@Override
	public void onResume() {
		super.onResume();
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		String username = prefs.getString(PreferencesProvider.KEY_USERNAME);
		if (username.equals(PreferencesProvider.DEFAULT_STRING)) {
			initLoginLayout();
		} else {
			initLogoutLayout(username);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onLoginFailure(String msg) {
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		String username = prefs.getString(PreferencesProvider.KEY_USERNAME);
		if (username.equals(PreferencesProvider.DEFAULT_STRING)) {
			initLoginLayout();
		} else {
			initLogoutLayout(username);
		}
		if (msg != null) {
			mError.setText(msg);
		}

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		isAttached = true;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		isAttached = false;
	}

	public void onLoginSuccessful() {
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		String username = prefs.getString(PreferencesProvider.KEY_USERNAME);
		if (username.equals(PreferencesProvider.DEFAULT_STRING)) {
			initLoginLayout();
		} else {
			initLogoutLayout(username);
		}
		mError.setText("");
	}

	public void onLogout() {
		initLoginLayout();
	}
}
