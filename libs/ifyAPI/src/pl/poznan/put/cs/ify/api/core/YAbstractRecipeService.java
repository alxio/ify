package pl.poznan.put.cs.ify.api.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.ServiceHandler.ServiceCommunication;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public abstract class YAbstractRecipeService extends Service implements
		IYRecipeHost, ILoginCallback, ServiceCommunication {

	public static final String ACTION_SEND_TEXT = "pl.poznan.put.cs.ify.ACTION_SEND_TEXT";
	public static final String Recipe_LOGS = "pl.poznan.put.cs.ify.Recipe_LOGS";
	public static final String Recipe_TAG = "pl.poznan.put.cs.ify.Recipe_TAG";
	public static final String ACTION_Recipe_LOGS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_Recipe_LOGS_RESPONSE";
	public static final String ACTION_Recipe_LOGS = "pl.poznan.put.cs.ify.ACTION_Recipe_LOGS_RESPONSE";

	public static final String Recipe = "pl.poznan.put.cs.ify.Recipe";
	public static final String PARAMS = "pl.poznan.put.cs.ify.PARAMS";
	public static final String Recipe_INFOS = "pl.poznan.put.cs.ify.Recipe_INFOS";

	public static final String TOGGLE_LOG = "pl.poznan.put.cs.ify.TOGGLE_LOG";
	public static final String TEXT = "pl.poznan.put.cs.ify.TEXT";
	public static final String INFO = "pl.poznan.put.cs.ify.INFO";
	private static final String REQUEST_LOGS = "pl.poznan.put.cs.ify.REQUEST_ARCHIVE_LOGS";

	protected IAvailableRecipesManager mAvailableRecipesManager;
	protected IActiveRecipesProvider mActiveRecipesManager;
	protected YFeatureList mActiveFeatures = new YFeatureList();
	protected ILog mLog;
	protected int mRecipeID = 0;
	protected ISecurity mSecurity;
	private ServiceHandler mServiceHandler = new ServiceHandler(this);
	private Messenger mMessenger = new Messenger(mServiceHandler);
	private BroadcastReceiver mToggleLogReceiver;
	private BroadcastReceiver mSendTextReceiver;
	private BroadcastReceiver mGetLogsReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		mAvailableRecipesManager = getAvailableRecipesManager();
		mActiveRecipesManager = getActiveRecipesManager();
		mSecurity = getSecurityManager();
		mLog = getLogManager();
		registerLogUtilsReceiver();
	}

	protected abstract ISecurity getSecurityManager();

	protected abstract IActiveRecipesProvider getActiveRecipesManager();

	protected abstract IAvailableRecipesManager getAvailableRecipesManager();

	protected abstract ILog getLogManager();

	@Override
	public int enableRecipe(String name, YParamList params) {
		int id = ++mRecipeID;
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		YRecipe recipe = mAvailableRecipesManager.getRecipe(name).newInstance();
		long feats = recipe.requestFeatures();
		Log.d("SERVICE", "Feats: " + Long.toHexString(feats));
		YFeatureList features = new YFeatureList(feats);
		initFeatures(features);
		params.setFeatures(feats);
		recipe.initialize(this, params, features, id, timestamp);
		for (Entry<Long, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterRecipe: " + recipe.getName() + " to "
					+ entry.getKey());
			entry.getValue().registerRecipe(recipe);
		}
		YLog.d("SERVICE", "ActivateRecipe: " + recipe.getName() + " ,ID: " + id);
		mActiveRecipesManager.put(id, recipe);
		return id;
	}

	protected void initFeatures(YFeatureList features) {
		Log.i("SERVICE", "Initializing feats");
		for (Entry<Long, YFeature> entry : features) {
			Long featId = entry.getKey();
			YFeature feat = mActiveFeatures.get(featId);
			Log.i("SERVICE", "initializing" + Long.toHexString(featId));
			if (feat != null) {
				Log.i("SERVICE", Long.toHexString(feat.getId())
						+ "already initialized");
				entry.setValue(feat);
			} else {
				feat = entry.getValue();
				feat.initialize(this);
				Log.i("SERVICE", "initialized" + Long.toHexString(feat.getId()));
				mActiveFeatures.add(feat);
			}
		}
		Log.i("SERVICE", "Initializing feats finished");
	}

	@Override
	public void disableRecipe(Integer id) {
		YRecipe recipe = mActiveRecipesManager.get(id);
		List<Long> toDelete = new ArrayList<Long>();
		for (Entry<Long, YFeature> entry : recipe.getFeatures()) {
			YFeature feat = entry.getValue();
			YLog.d("SERVICE", "UnregisterRecipe: " + recipe.getName()
					+ " from " + entry.getKey());
			feat.unregisterRecipe(recipe);
			if (!feat.isUsed()) {
				toDelete.add(entry.getKey());
				YLog.d("SERVICE", "UninitializeFeature: " + feat.getId());
				feat.uninitialize();
			}
		}
		mActiveFeatures.removeAll(toDelete);
		mActiveRecipesManager.remove(id);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mToggleLogReceiver);
		unregisterReceiver(mSendTextReceiver);
		unregisterReceiver(mGetLogsReceiver);
		pauseAll();
	}

	public Bundle getAvaibleRecipesBundle() {
		Bundle b = new Bundle();
		for (Entry<String, YRecipe> entry : mAvailableRecipesManager
				.getAvailableRecipesMap().entrySet()) {
			String recipeName = entry.getKey();
			YParamList params = new YParamList();
			params.setFeatures(entry.getValue().requestFeatures());
			entry.getValue().requestParams(params);
			b.putParcelable(recipeName, params);
		}
		return b;
	}

	protected void pauseAll() {
		ArrayList<Long> toDelete = new ArrayList<Long>();
		for (Entry<Integer, YRecipe> e : mActiveRecipesManager.getMap()
				.entrySet()) {
			YRecipe recipe = e.getValue();
			for (Entry<Long, YFeature> entry : recipe.getFeatures()) {
				YFeature feat = entry.getValue();
				YLog.d("SERVICE", "UnregisterRecipe: " + recipe.getName()
						+ " from " + entry.getKey());
				feat.unregisterRecipe(recipe);
				if (!feat.isUsed()) {
					toDelete.add(entry.getKey());
					YLog.d("SERVICE", "UninitializeFeature: " + feat.getId());
					feat.uninitialize();
				}
			}
		}
		mActiveFeatures.removeAll(toDelete);
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void sendArchivedLogs(String tag) {
		if (tag != null) {
			Log.d("SendLogs", "" + tag);
			Intent i = new Intent(ACTION_Recipe_LOGS_RESPONSE);
			i.putExtra(Recipe_TAG, tag);
			i.putExtra(Recipe_LOGS, YLog.getFilteredHistory(tag));
			sendBroadcast(i);
		}
	}

	@Override
	public ISecurity getSecurity() {
		return mSecurity;
	}

	@Override
	public abstract int getNotificationIconId();

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	@Override
	public void onLoginSuccess(String username) {
		Message msg = Message.obtain(null, ServiceHandler.RESULT_LOGIN);
		msg.arg1 = ServiceHandler.SUCCESS;
		try {
			mServiceHandler.getActivityMessenger().send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLoginFail(String message) {
		Message msg = Message.obtain(null, ServiceHandler.RESULT_LOGIN);
		msg.arg1 = ServiceHandler.FAILURE;
		Bundle b = new Bundle();
		b.putString(ServiceHandler.MESSAGE, message);
		msg.setData(b);
		try {
			mServiceHandler.getActivityMessenger().send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRegisterRecipeRequest(Bundle data) {
		data.setClassLoader(getClassLoader());
		String name = data.getString(Recipe);
		Log.d("SERVICE", "EnableRecipe: " + name);
		YParamList params = data.getParcelable(PARAMS);
		enableRecipe(name, params);
	}

	@Override
	public void onRequestAvailableRecipes() {
		Log.d("TEMP", "onRequestAvailableRecipes");
		mAvailableRecipesManager.refresh();
		Message msg = Message.obtain(null,
				ServiceHandler.RESPONSE_AVAILABLE_RECIPES);
		msg.setData(getAvaibleRecipesBundle());
		Log.d("TEMP", "onRequestAvailableRecipes sending");

		try {
			mServiceHandler.getActivityMessenger().send(msg);
		} catch (RemoteException e) {
			Log.e("temp", e.toString());
		}
	}

	@Override
	public void onDisableRecipeRequest(int id) {
		if (id != -1) {
			disableRecipe(id);
			sendActiveRecipes();
		}
	}

	private void sendActiveRecipes() {
		Message msg = Message.obtain(null,
				ServiceHandler.RESPONSE_ACTIVE_RECIPES);
		ArrayList<ActiveRecipeInfo> activeRecipeInfos = new ArrayList<ActiveRecipeInfo>();
		for (Entry<Integer, YRecipe> recipe : mActiveRecipesManager.getMap()
				.entrySet()) {
			ActiveRecipeInfo activeRecipeInfo = new ActiveRecipeInfo(recipe
					.getValue().getName(), recipe.getValue().getParams(),
					recipe.getKey());
			activeRecipeInfos.add(activeRecipeInfo);
		}
		Bundle b = new Bundle();
		b.putParcelableArrayList(Recipe_INFOS, activeRecipeInfos);
		msg.setData(b);
		try {
			mServiceHandler.getActivityMessenger().send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestActiveRecipes() {
		sendActiveRecipes();
	}

	@Override
	public void onRequestRestartAllGroupRecipes() {
		PreferencesProvider prefs = PreferencesProvider.getInstance(this);
		if (mActiveFeatures.getGroup() != null) {
			mActiveFeatures.getGroup().changeGroupServer(
					prefs.getString(PreferencesProvider.KEY_SERVER_URL));
		}
	}

	@Override
	public void onRequestLogin(String username, String password) {
		mSecurity.login(username, password, this);
	}

	@Override
	public void onRequestLogout() {
		mSecurity.logout(this);
	}

	private void registerLogUtilsReceiver() {
		IntentFilter f = new IntentFilter(TOGGLE_LOG);
		mToggleLogReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				YLog.toggleView();
			}
		};
		registerReceiver(mToggleLogReceiver, f);

		mSendTextReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ActiveRecipeInfo info = intent.getParcelableExtra(INFO);
				String text = intent.getStringExtra(TEXT);
				YLog.d("SERVICE", "Text to recipe" + info.getId());
				mActiveRecipesManager.get(info.getId()).tryHandleEvent(
						new YTextEvent(text));
			}
		};
		IntentFilter sendTextFilter = new IntentFilter(ACTION_SEND_TEXT);
		registerReceiver(mSendTextReceiver, sendTextFilter);

		mGetLogsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				mAvailableRecipesManager.refresh();
				String tag = intent.getStringExtra(Recipe_TAG);
				sendArchivedLogs(tag);
			}
		};
		IntentFilter getLogsFilter = new IntentFilter(REQUEST_LOGS);
		registerReceiver(mGetLogsReceiver, getLogsFilter);
	}

	@Override
	public void onLogout() {
		Message msg = Message.obtain(null, ServiceHandler.LOGOUT);
		try {
			mServiceHandler.getActivityMessenger().send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
