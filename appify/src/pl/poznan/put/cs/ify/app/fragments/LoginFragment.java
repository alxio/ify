package pl.poznan.put.cs.ify.app.fragments;

import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YReceiptsService;
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
	private BroadcastReceiver mBroadcastRec;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_login, null);
		initFields(v);
		initLoginLayout();

		IntentFilter f = new IntentFilter(YReceiptsService.RESPONSE_LOGIN);
		mBroadcastRec = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("LOGIN", "Success receiver");
				Bundle ex = intent.getExtras();
				String user = ex.getString("username");
				if (user != null) {
					initLogoutLayout(user);
				} else {
					String error = ex.getString("error");
					mError.setText(error);
					mError.setVisibility(View.VISIBLE);
				}
			}
		};
		getActivity().registerReceiver(mBroadcastRec, f);

		Intent i = new Intent();
		i.setAction(YReceiptsService.ACTION_GET_USER);
		getActivity().sendBroadcast(i);
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

	private void initLogoutLayout(String username) {
		mLoginLayout.setVisibility(View.GONE);
		mLogoutLayout.setVisibility(View.VISIBLE);
		mUsername.setText(username);
	}

	private void initLoginLayout() {
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
			Intent i = new Intent();
			i.putExtra("username", mLogin.getText().toString());
			i.putExtra("password", mPassword.getText().toString());
			i.setAction(YReceiptsService.ACTION_LOGIN);
			Log.d("LOGIN", "Login sending");
			getActivity().sendBroadcast(i);
		}
	}

	public void logout(View v) {
		Intent i = new Intent();
		i.setAction(YReceiptsService.ACTION_LOGOUT);
		getActivity().sendBroadcast(i);
		initLoginLayout();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mBroadcastRec != null) {
			getActivity().unregisterReceiver(mBroadcastRec);
		}
	}
}
