package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends YActivity {
	View mLoginLayout;
	View mLogoutLayout;
	TextView mError;
	TextView mUsername;
	EditText mLogin;
	EditText mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initFields();
		initLoginLayout();

		IntentFilter f = new IntentFilter(YRecipesService.RESPONSE_LOGIN);
		BroadcastReceiver b = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("LOGIN","Success receiver");
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
		registerReceiver(b, f);
		
		Intent i = new Intent();
		i.setAction(YRecipesService.ACTION_GET_USER);
		sendBroadcast(i);
	}

	private void initFields() {
		mLoginLayout = findViewById(R.id.layLogin);
		mLogoutLayout = findViewById(R.id.layLogout);
		mUsername = (TextView) findViewById(R.id.username);
		mLogin = (EditText) findViewById(R.id.login);
		mPassword = (EditText) findViewById(R.id.password);
		mError = (TextView) findViewById(R.id.error);
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
			i.setAction(YRecipesService.ACTION_LOGIN);
			Log.d("LOGIN","Login sending");
			sendBroadcast(i);
		}
	}

	public void logout(View v) {
		Intent i = new Intent();
		i.setAction(YRecipesService.ACTION_LOGOUT);
		sendBroadcast(i);
		initLoginLayout();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
