package pl.poznan.put.cs.ify.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.security.User;
import pl.poznan.put.cs.ify.api.security.YSecurity;
import pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.app.RecipeFromDatabase;
import pl.poznan.put.cs.ify.app.RecipesDatabaseHelper;
import pl.poznan.put.cs.ify.app.ui.InitializedRecipeDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ServiceHandler.ServiceCommunication;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

@SuppressLint("UseSparseArrays")
public class YRecipesService extends Service implements IYRecipeHost,
		ILoginCallback, ServiceCommunication {
	public static final String PARAMS = "pl.poznan.put.cs.ify.PARAMS";
	public static final String Recipe = "pl.poznan.put.cs.ify.Recipe";
	public static final String Recipe_INFOS = "pl.poznan.put.cs.ify.Recipe_INFOS";
	public static final String ACTION_ACTIVATE_Recipe = "pl.poznan.put.cs.ify.ACTION_ACTIVATE_Recipe";
	public static final String ACTION_GET_RecipeS_REQUEST = "pl.poznan.put.cs.ify.ACTION_GET_RecipeS_REQ";
	public static final String ACTION_GET_RecipeS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_GET_RecipeS_RESP";
	public static final String ACTION_DEACTIVATE_Recipe = "pl.poznan.put.cs.ify.ACTION_DEACTIVATE_Recipe";
	public static final String Recipe_ID = "pl.poznan.put.cs.ify.Recipe_ID";
	public static final String Recipe_LOGS = "pl.poznan.put.cs.ify.Recipe_LOGS";
	public static final String AVAILABLE_RESPONSE = "pl.poznan.put.cs.ify.AVAILABLE_RESPONSE";
	public static final String AVAILABLE_REQUEST = "pl.poznan.put.cs.ify.AVAILABLE_REQUEST";
	public static final String AVAILABLE_RecipeS = "pl.poznan.put.cs.ify.AVAILABLE_RecipeS";
	public static final String ACTION_Recipe_LOGS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_Recipe_LOGS_RESPONSE";
	public static final String ACTION_Recipe_LOGS = "pl.poznan.put.cs.ify.Recipe_LOGS";
	public static final String TOGGLE_LOG = "pl.poznan.put.cs.ify.TOGGLE_LOG";
	public static final String Recipe_TAG = "pl.poznan.put.cs.ify.Recipe_TAG";
	public static final String ACTION_SEND_TEXT = "pl.poznan.put.cs.ify.ACTION_SEND_TEXT";
	public static final String ACTION_LOGIN = "pl.poznan.put.cs.ify.ACTION_LOGIN";
	public static final String RESPONSE_LOGIN = "pl.poznan.put.cs.ify.RESPONSE_LOGIN";
	public static final String ACTION_LOGOUT = "pl.poznan.put.cs.ify.ACTION_LOGOUT";
	public static final String ACTION_GET_USER = "pl.poznan.put.cs.ify.ACTION_GET_USER";

	private int NOTIFICATION = R.string.app_name;

	private AvailableRecipesManager mManager;
	private Map<Integer, YRecipe> mActiveRecipes = new HashMap<Integer, YRecipe>();
	private YFeatureList mActiveFeatures = new YFeatureList();
	private NotificationManager mNM;
	@SuppressWarnings("unused")
	private YLog mLog;
	private int mRecipeID = 0;
	private YSecurity mSecurity;

	@Override
	public void onCreate() {
		super.onCreate();
		mManager = new AvailableRecipesManager(this);
		mSecurity = new YSecurity(this);
		mLog = new YLog(this);
		Log.d("LIFECYCLE", this.toString() + " onCreate");
		RecipesDatabaseHelper dbHelper = new RecipesDatabaseHelper(this);
		mRecipeID = dbHelper.getMaxId();
		List<RecipeFromDatabase> activatedRecipes = dbHelper
				.getActivatedRecipes();
		for (RecipeFromDatabase recipeFromDatabase : activatedRecipes) {
			reviveRecipe(recipeFromDatabase);
		}
		Log.d("RecipeS_DB", mRecipeID + " init");

		registerLogUtilsReceiver();
		registerRecipesUtilsReceiver();
		registerLoginReceiver();
		showNotification();
	}

	private int reviveRecipe(RecipeFromDatabase recipeFromDatabase) {
		YRecipe recipe = mManager.get(recipeFromDatabase.name).newInstance();
		long feats = recipe.requestFeatures();
		YFeatureList features = new YFeatureList(feats);
		initFeatures(features);
		recipeFromDatabase.yParams.setFeatures(feats);
		recipe.initialize(this, recipeFromDatabase.yParams, features,
				recipeFromDatabase.id, recipeFromDatabase.timestamp);
		for (Entry<Long, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterRecipe: " + recipe.getName() + " to "
					+ entry.getKey());
			entry.getValue().registerRecipe(recipe);
		}
		mActiveRecipes.put(recipeFromDatabase.id, recipe);
		showNotification();
		return recipeFromDatabase.id;
	}

	private void registerRecipesUtilsReceiver() {

		BroadcastReceiver getLogsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				mManager.refresh();
				String tag = intent.getStringExtra(Recipe_TAG);
				sendLogs(tag);
			}
		};
		IntentFilter getLogsFilter = new IntentFilter(AVAILABLE_REQUEST);
		registerReceiver(getLogsReceiver, getLogsFilter);

		BroadcastReceiver sendTextReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ActiveRecipeInfo info = intent
						.getParcelableExtra(InitializedRecipeDialog.INFO);
				String text = intent
						.getStringExtra(InitializedRecipeDialog.TEXT);
				YLog.d("SERVICE", "Text to recipe" + info.getId());
				mActiveRecipes.get(info.getId()).tryHandleEvent(
						new YTextEvent(text));
			}
		};
		IntentFilter sendTextFilter = new IntentFilter(ACTION_SEND_TEXT);
		registerReceiver(sendTextReceiver, sendTextFilter);
	}

	public void sendLogs(String tag) {
		if (tag != null) {
			Log.d("SendLogs", "" + tag);
			Intent i = new Intent(ACTION_Recipe_LOGS_RESPONSE);
			i.putExtra(Recipe_TAG, tag);
			i.putExtra(Recipe_LOGS, YLog.getFilteredHistory(tag));
			sendBroadcast(i);
		}
	}

	private void registerLogUtilsReceiver() {
		IntentFilter f = new IntentFilter(TOGGLE_LOG);
		BroadcastReceiver b = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				YLog.toggleView();
			}
		};
		registerReceiver(b, f);
	}

	private void registerLoginReceiver() {
		IntentFilter f = new IntentFilter(ACTION_LOGIN);
		BroadcastReceiver b = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("LOGIN", "Login received");
				Bundle ex = intent.getExtras();
				String username = ex.getString("username");
				String password = ex.getString("password");
				mSecurity.login(username, password, YRecipesService.this);
			}
		};
		registerReceiver(b, f);

		IntentFilter f2 = new IntentFilter(ACTION_LOGOUT);
		BroadcastReceiver b2 = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				mSecurity.logout();
				List<Integer> toDisable = new ArrayList<Integer>();
				for (Entry<Integer, YRecipe> e : mActiveRecipes.entrySet()) {
					YRecipe rec = e.getValue();
					if ((rec.requestFeatures() & Y.Group) != 0)
						toDisable.add(e.getKey());
				}
				for (Integer id : toDisable) {
					disableRecipe(id);
				}
			}
		};
		registerReceiver(b2, f2);

		IntentFilter f3 = new IntentFilter(ACTION_GET_USER);
		BroadcastReceiver b3 = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				User user = mSecurity.getCurrentUser();
				if (user != null) {
					onLoginSuccess(user.name);
				}
			}
		};
		registerReceiver(b3, f3);
	}

	@Override
	public void onLoginSuccess(String username) {
		Log.d("LOGIN", "Success sending");
		Intent i = new Intent();
		i.putExtra("username", username);
		i.setAction(RESPONSE_LOGIN);
		sendBroadcast(i);
	}

	@Override
	public void onLoginFail(String message) {
		Intent i = new Intent();
		i.putExtra("error", message);
		i.setAction(RESPONSE_LOGIN);
		sendBroadcast(i);
	}

	@SuppressWarnings("deprecation")
	private void showNotification() {
		int active = mActiveRecipes.size();
		int icon;
		switch (active) {
		case 0:
			icon = R.drawable.ify;
			break;
		case 1:
			icon = R.drawable.y1;
			break;
		case 2:
			icon = R.drawable.y2;
			break;
		case 3:
			icon = R.drawable.y3;
			break;
		case 4:
			icon = R.drawable.y4;
			break;
		case 5:
			icon = R.drawable.y5;
			break;
		case 6:
			icon = R.drawable.y6;
			break;
		case 7:
			icon = R.drawable.y7;
			break;
		case 8:
			icon = R.drawable.y8;
			break;
		case 9:
			icon = R.drawable.y9;
			break;
		default:
			icon = R.drawable.y10;
			break;
		}
		CharSequence text = getText(NOTIFICATION);

		Notification notification = new Notification(icon, text,
				System.currentTimeMillis());
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("POSITION", 1);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);

		notification.setLatestEventInfo(this, text,
				"Active recipes: " + active, contentIntent);

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNM.notify(NOTIFICATION, notification);
	}

	public Bundle getAvaibleRecipesBundle() {
		Bundle b = new Bundle();
		for (Entry<String, YRecipe> entry : mManager.getAvailableReceipesMap()
				.entrySet()) {
			String recipeName = entry.getKey();
			YParamList params = new YParamList();
			params.setFeatures(entry.getValue().requestFeatures());
			entry.getValue().requestParams(params);
			b.putParcelable(recipeName, params);
		}
		return b;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("LIFECYCLE", this.toString() + " onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public int enableRecipe(String name, YParamList params) {
		int id = ++mRecipeID;
		Log.d("RecipeS_DB", id + "");
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		YRecipe recipe = mManager.get(name).newInstance();
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
		mActiveRecipes.put(id, recipe);
		showNotification();
		RecipesDatabaseHelper recipesHelper = new RecipesDatabaseHelper(this);
		recipesHelper.saveRecipe(recipe, id);
		return id;
	}

	private void initFeatures(YFeatureList features) {
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
		YRecipe recipe = getActiveRecipes().get(id);
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
		RecipesDatabaseHelper recipesHelper = new RecipesDatabaseHelper(this);
		recipesHelper.removeRecipe(id);
		YLog.d("SERVICE", "DeactivateRecipe: " + recipe.getName() + " ,ID: "
				+ id);
		mActiveRecipes.remove(id);
		showNotification();
	}

	private Map<Integer, YRecipe> getActiveRecipes() {
		return Collections.unmodifiableMap(mActiveRecipes);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("LIFECYCLE", "onDestroy");
		mNM.cancel(NOTIFICATION);

		pauseAll();
	}

	private void pauseAll() {
		ArrayList<Long> toDelete = new ArrayList<Long>();
		for (Entry<Integer, YRecipe> e : getActiveRecipes().entrySet()) {
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

	public AvailableRecipesManager getAvaibleRecipesManager() {
		return mManager;
	}

	public void updateAvailableRecipes() {
		mManager.refresh();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	@Override
	public YSecurity getSecurity() {
		return mSecurity;
	}

	private ServiceHandler mServiceHandler = new ServiceHandler(this);
	private Messenger mMessenger = new Messenger(mServiceHandler);

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
		mManager.refresh();
		Message msg = Message.obtain(null,
				ActivityHandler.AVAILABLE_RecipeS_RESPONSE);
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
				ActivityHandler.ACTIVE_RECIPES_RESPONSE);
		ArrayList<ActiveRecipeInfo> activeRecipeInfos = new ArrayList<ActiveRecipeInfo>();
		for (Entry<Integer, YRecipe> recipe : mActiveRecipes.entrySet()) {
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
	public int getNotificationIconId() {
		return R.drawable.ify_n;
	}

	@Override
	public void onRequestRestartAllGroupRecipes() {
		PreferencesProvider prefs = PreferencesProvider.getInstance(this);
		if (mActiveFeatures.getGroup() != null) {
			mActiveFeatures.getGroup().changeGroupServer(
					prefs.getString(PreferencesProvider.KEY_SERVER_URL));
		}
	}
}