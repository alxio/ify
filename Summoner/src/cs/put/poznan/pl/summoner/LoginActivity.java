package cs.put.poznan.pl.summoner;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.core.ActivityHandler;
import pl.poznan.put.cs.ify.api.core.ActivityHandler.ActivityCommunication;
import pl.poznan.put.cs.ify.api.core.ServiceHandler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import cs.put.poznan.pl.summoner.ify.SummonerService;

public class LoginActivity extends ActionBarActivity implements
		ActivityCommunication {

	private EditText mUsernameET;
	private Button mLoginButton;
	private EditText mPasswordED;
	private View mProgressView;
	private boolean mBound;
	private TextView mErrorTextView;
	private ActivityHandler mActivityHandler = new ActivityHandler(this);
	private Messenger mActivityMessenger = new Messenger(mActivityHandler);
	protected Messenger mService;

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the object we can use to
			// interact with the service. We are communicating with the
			// service using a Messenger, so here we get a client-side
			// representation of that from the raw IBinder object.
			mService = new Messenger(service);
			mBound = true;

			Message msg = Message.obtain(null, ServiceHandler.REGISTER_CLIENT);
			try {
				msg.replyTo = mActivityMessenger;
				mService.send(msg);
			} catch (RemoteException e) {
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			mService = null;
			mBound = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!PreferencesProvider.getInstance(this)
				.getString(PreferencesProvider.KEY_USERNAME)
				.equals(PreferencesProvider.DEFAULT_STRING)) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
			return;
		}
		setContentView(R.layout.activity_login);
		mUsernameET = (EditText) findViewById(R.id.login_username);
		mPasswordED = (EditText) findViewById(R.id.login_password);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mProgressView = (ProgressBar) findViewById(R.id.login_progress);
		mErrorTextView = (TextView) findViewById(R.id.login_error_textview);

		
		mUsernameET.setText("summoner-admin");
		mPasswordED.setText("qwert");
		Intent i = new Intent(this, SummonerService.class);
		startService(i);
	}

	public void login(View v) {
		String username = mUsernameET.getText().toString();
		String password = mPasswordED.getText().toString();

		if (username.isEmpty() || password.isEmpty()) {
			return;
		}
		mErrorTextView.setText("");
		mErrorTextView.setVisibility(View.INVISIBLE);

		mProgressView.setVisibility(View.VISIBLE);
		mLoginButton.setVisibility(View.GONE);

		if (mService != null) {
			Message msg = Message.obtain();
			Bundle b = new Bundle();
			msg.what = ServiceHandler.REQUEST_LOGIN;
			b.putString(ServiceHandler.LOGIN, username);
			b.putString(ServiceHandler.PASSWD, password);
			msg.setData(b);
			try {
				mService.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
				mProgressView.setVisibility(View.GONE);
				mLoginButton.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		bindService(new Intent(this, SummonerService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(mConnection);
			mBound = false;
		}
	}

	@Override
	public void onAvailableRecipesListReceived(Bundle data) {
	}

	@Override
	public void onActiveRecipesListReceiverd(Bundle data) {
	}

	@Override
	public void onLoginFailure(String string) {
		mErrorTextView.setText(string);
		mErrorTextView.setVisibility(View.VISIBLE);
		mProgressView.setVisibility(View.GONE);
		mLoginButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoginSuccessful() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void onLogout() {
	}
}
