package cs.put.poznan.pl.summoner;

import java.util.ArrayList;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.core.ActivityHandler;
import pl.poznan.put.cs.ify.api.core.ActivityHandler.ActivityCommunication;
import pl.poznan.put.cs.ify.api.core.ServiceHandler;
import pl.poznan.put.cs.ify.api.core.YAbstractRecipeService;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cs.put.poznan.pl.summoner.fragment.UsersListFragment;
import cs.put.poznan.pl.summoner.fragment.UsersMapFragment;
import cs.put.poznan.pl.summoner.ify.SummonerRecipe;
import cs.put.poznan.pl.summoner.ify.SummonerService;
import cs.put.poznan.pl.summoner.model.UserInfo;

public class MainActivity extends ActionBarActivity implements
		ActivityCommunication {

	protected static final int TAB_POS_LIST = 1;
	protected static final int TAB_POS_MAP = 0;
	private static final String DATA = "cs.put.poznan.pl.summoner.data";
	private static final String MAP_DATA = "cs.put.poznan.pl.summoner.data.map";

	private UsersListFragment mListFrag;
	private UsersMapFragment mMapFrag;
	private ArrayList<UserInfo> mData;

	private ActivityHandler mActivityHandler = new ActivityHandler(this);
	private Messenger mActivityMessenger = new Messenger(mActivityHandler);
	protected Messenger mService;

	private TabListener mTabListener = new TabListener() {

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (tab.getPosition() == TAB_POS_MAP) {
				setMapFrag();
			} else if (tab.getPosition() == TAB_POS_LIST) {
				setListFrag();
			}
		}

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		}
	};
	private BroadcastReceiver mRecipeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			extras.setClassLoader(getClassLoader());
			ArrayList<UserInfo> infos = new ArrayList<UserInfo>();
			UserInfo userInfo;
			for (String key : extras.keySet()) {
				userInfo = extras.getParcelable(key);
				infos.add(userInfo);
			}
			updateData(infos);
		}
	};
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			mBound = true;

			Message msg = Message.obtain(null, ServiceHandler.REGISTER_CLIENT);
			try {
				msg.replyTo = mActivityMessenger;
				mService.send(msg);

			} catch (RemoteException e) {
			}
			enableRecipe();
		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
			mBound = false;
		}
	};
	private boolean mBound;
	private EditText mStatusEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mData = savedInstanceState.getParcelableArrayList(DATA);
			if (mData != null) {
				mListFrag = UsersListFragment.getInstance(mData);
			}
			Bundle b = savedInstanceState.getBundle(MAP_DATA);
			if (b != null) {
				mMapFrag = UsersMapFragment.getNewInstance(b);
			}
		}
		Intent i = new Intent(this, SummonerService.class);
		startService(i);
		setContentView(R.layout.activity_main);
		initTabs();
		mStatusEditText = (EditText) findViewById(R.id.main_status_EditText);
		initRecipeReceiver();

	}

	protected void updateData(ArrayList<UserInfo> infos) {
		mData = infos;
		getListFrag().updateData(mData);
		getMapFrag().updateData(mData);
	}

	protected void enableRecipe() {
		Message msg = Message.obtain(null, ServiceHandler.REGISTER_Recipe);
		Bundle bundle = new Bundle();
		bundle.putString(YAbstractRecipeService.Recipe, "SummonerRecipe");
		bundle.putParcelable(YAbstractRecipeService.PARAMS, new YParamList());
		msg.setData(bundle);
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	protected void disalbeRecipe() {
		Message msg = Message.obtain(null,
				ServiceHandler.REQUEST_DISABLE_RECIPE);
		msg.arg1 = 0;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void initRecipeReceiver() {
		IntentFilter filter = new IntentFilter(SummonerService.DATA);
		registerReceiver(mRecipeReceiver, filter);
	}

	private void initTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab mapTab = actionBar.newTab().setText(R.string.main_map)
				.setTabListener(mTabListener);
		actionBar.addTab(mapTab);

		Tab listTab = actionBar.newTab().setText(R.string.main_list)
				.setTabListener(mTabListener);
		actionBar.addTab(listTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logout) {
			logout();
			return true;
		} else if (item.getItemId() == R.id.action_refresh) {
			Intent i = new Intent();
			i.setAction(YAbstractRecipeService.ACTION_SEND_TEXT);
			i.putExtra(YAbstractRecipeService.TEXT, SummonerRecipe.REFRESH);
			sendBroadcast(i);
		}
		return super.onOptionsItemSelected(item);
	}

	private void logout() {
		PreferencesProvider.getInstance(this).putString(
				PreferencesProvider.KEY_USERNAME,
				PreferencesProvider.DEFAULT_STRING);
		PreferencesProvider.getInstance(this).putString(
				PreferencesProvider.KEY_HASH,
				PreferencesProvider.DEFAULT_STRING);
		Message msg = Message.obtain();
		Bundle b = new Bundle();
		msg.what = ServiceHandler.REQUEST_LOGOUT;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	public void sendStatus(View v) {
		if (!mStatusEditText.getText().toString().isEmpty()) {
			Intent i = new Intent();
			i.setAction(YAbstractRecipeService.ACTION_SEND_TEXT);
			i.putExtra(YAbstractRecipeService.TEXT, mStatusEditText.getText()
					.toString());
			sendBroadcast(i);
			mStatusEditText.setText("");
		} else {
			Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setListFrag() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frag_container, getListFrag());
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	private UsersListFragment getListFrag() {
		if (mListFrag == null) {
			mListFrag = UsersListFragment.getInstance();
		}
		return mListFrag;
	}

	private void setMapFrag() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frag_container, getMapFrag());
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	private UsersMapFragment getMapFrag() {
		if (mMapFrag == null) {
			mMapFrag = UsersMapFragment.getNewInstance();
		}
		return mMapFrag;
	}

	public void onUserSelected(final UserInfo u) {
		if (u.getLatLng() != null) {
		getMapFrag().setSelectdPos(u.getLatLng());
		getSupportActionBar().setSelectedNavigationItem(TAB_POS_MAP);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mRecipeReceiver);
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
	}

	@Override
	public void onLoginSuccessful() {
	}

	@Override
	public void onLogout() {
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBundle(MAP_DATA, getMapFrag().getArguments());
		outState.putParcelableArrayList(DATA, mData);
	}
}
