package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.bu;

import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.fragments.InitializedReceipesFragment;
import pl.poznan.put.cs.ify.app.fragments.LoginFragment;
import pl.poznan.put.cs.ify.app.fragments.MarketFragment;
import pl.poznan.put.cs.ify.app.fragments.RecipesListFragment;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActivityHandler;
import pl.poznan.put.cs.ify.core.ActivityHandler.ActivityCommunication;
import pl.poznan.put.cs.ify.core.ActiveReceiptInfo;
import pl.poznan.put.cs.ify.core.ServiceHandler;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActivityCommunication {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private RecipesListFragment mRecipesListFragment;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mTitles;
	private MarketFragment mMarketFrag;
	private InitializedReceipesFragment mActiveRecipesFrag;
	private LoginFragment mLoginFrag;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments

		Fragment f = null;
		switch (position) {
		case 0:
			f = getAvailableRecipesFrag();
			break;
		case 1:
			f = getActiveRecipesFrag();
			break;
		case 2:
			f = getMarketFrag();
			break;
		case 3:
			f = getLoginFrag();
		default:
			break;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, f).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	private LoginFragment getLoginFrag() {
		if (mLoginFrag == null) {
			mLoginFrag = new LoginFragment();
		}
		return mLoginFrag;
	}

	private InitializedReceipesFragment getActiveRecipesFrag() {
		if (mActiveRecipesFrag == null) {
			mActiveRecipesFrag = new InitializedReceipesFragment();
		}
		return mActiveRecipesFrag;
	}

	private MarketFragment getMarketFrag() {
		if (mMarketFrag == null) {
			mMarketFrag = new MarketFragment();
		}
		return mMarketFrag;
	}

	private RecipesListFragment getAvailableRecipesFrag() {
		if (mRecipesListFragment == null) {
			mRecipesListFragment = new RecipesListFragment();
		}
		return mRecipesListFragment;
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarToggle, you must call it during onPostCreate()
	 * and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/** Messenger for communicating with the service. */
	private Messenger mService = null;

	/** Flag indicating whether we have called bind on the service. */
	private boolean mBound;

	private ActivityHandler mActivityHandler = new ActivityHandler(this);
	private Messenger mActivityMessenger = new Messenger(mActivityHandler);

	/**
	 * Class for interacting with the main interface of the service.
	 */
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

			requestReceiptsList();
			requestActiveRecipesList();
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
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mTitles = getResources().getStringArray(R.array.drawer_menu);
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		// Bind to the service
		bindService(new Intent(this, YReceiptsService.class), mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(mConnection);
			mBound = false;
		}
	}

	public void activateReceipt(String receipt, YParamList requiredParams) {
		Message msg = Message.obtain(null, ServiceHandler.REGISTER_RECEIPT);
		Bundle bundle = new Bundle();
		bundle.putString(YReceiptsService.RECEIPT, receipt);
		bundle.putParcelable(YReceiptsService.PARAMS, requiredParams);
		msg.setData(bundle);
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void requestReceiptsList() {
		if (mService != null) {
			Message msg = Message.obtain(null, ServiceHandler.REQUEST_AVAILABLE_RECEIPTS);
			try {
				mService.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onAvailableRecipesListReceived(Bundle data) {
		List<YReceiptInfo> listFromBundle = YReceiptInfo.listFromBundle(data, getClassLoader());
		mRecipesListFragment.onReceiptsListUpdated(listFromBundle);
	}

	public void disableRecipe(int id) {
		Message msg = Message.obtain(null, ServiceHandler.REQUEST_DISABLE_RECIPE);
		msg.arg1 = id;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActiveRecipesListReceiverd(Bundle data) {
		data.setClassLoader(getClassLoader());
		ArrayList<ActiveReceiptInfo> activeReceiptInfos = data.getParcelableArrayList(YReceiptsService.RECEIPT_INFOS);
		getActiveRecipesFrag().updateData(activeReceiptInfos);

	}

	public void requestActiveRecipesList() {
		if (mService != null) {
			Message msg = Message.obtain(null, ServiceHandler.REQUEST_ACTIVE_RECIPES);
			try {
				mService.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// TODO: Quick fix
	public void login(View v) {
		mLoginFrag.login(v);
	}

	public void logout(View v) {
		mLoginFrag.logout(v);
	}
}
