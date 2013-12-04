package pl.poznan.put.cs.ify.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YTextEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.security.User;
import pl.poznan.put.cs.ify.api.security.YSecurity;
import pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback;
import pl.poznan.put.cs.ify.app.InitializedReceipesActivity;
import pl.poznan.put.cs.ify.app.ReceiptFromDatabase;
import pl.poznan.put.cs.ify.app.ReceiptsDatabaseHelper;
import pl.poznan.put.cs.ify.app.ui.InitializedReceiptDialog;
import pl.poznan.put.cs.ify.appify.R;
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
import android.util.Log;

@SuppressLint("UseSparseArrays")
public class YReceiptsService extends Service implements IYReceiptHost, ILoginCallback {
	public static final String PARAMS = "pl.poznan.put.cs.ify.PARAMS";
	public static final String RECEIPT = "pl.poznan.put.cs.ify.RECEIPT";
	public static final String RECEIPT_INFOS = "pl.poznan.put.cs.ify.RECEIPT_INFOS";
	public static final String ACTION_ACTIVATE_RECEIPT = "pl.poznan.put.cs.ify.ACTION_ACTIVATE_RECEIPT";
	public static final String ACTION_GET_RECEIPTS_REQUEST = "pl.poznan.put.cs.ify.ACTION_GET_RECEIPTS_REQ";
	public static final String ACTION_GET_RECEIPTS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_GET_RECEIPTS_RESP";
	public static final String ACTION_DEACTIVATE_RECEIPT = "pl.poznan.put.cs.ify.ACTION_DEACTIVATE_RECEIPT";
	public static final String RECEIPT_ID = "pl.poznan.put.cs.ify.RECEIPT_ID";
	public static final String RECEIPT_LOGS = "pl.poznan.put.cs.ify.RECEIPT_LOGS";
	public static final String AVAILABLE_RESPONSE = "pl.poznan.put.cs.ify.AVAILABLE_RESPONSE";
	public static final String AVAILABLE_REQUEST = "pl.poznan.put.cs.ify.AVAILABLE_REQUEST";
	public static final String AVAILABLE_RECEIPTS = "pl.poznan.put.cs.ify.AVAILABLE_RECEIPTS";
	public static final String ACTION_RECEIPT_LOGS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_RECEIPT_LOGS_RESPONSE";
	public static final String ACTION_RECEIPT_LOGS = "pl.poznan.put.cs.ify.RECEIPT_LOGS";
	public static final String TOGGLE_LOG = "pl.poznan.put.cs.ify.TOGGLE_LOG";
	public static final String RECEIPT_TAG = "pl.poznan.put.cs.ify.RECEIPT_TAG";
	public static final String ACTION_SEND_TEXT = "pl.poznan.put.cs.ify.ACTION_SEND_TEXT";
	public static final String ACTION_LOGIN = "pl.poznan.put.cs.ify.ACTION_LOGIN";
	public static final String RESPONSE_LOGIN = "pl.poznan.put.cs.ify.RESPONSE_LOGIN";
	public static final String ACTION_LOGOUT = "pl.poznan.put.cs.ify.ACTION_LOGOUT";
	public static final String ACTION_GET_USER = "pl.poznan.put.cs.ify.ACTION_GET_USER";

	private int NOTIFICATION = R.string.app_name;

	private AvailableRecipesManager mManager;
	private Map<Integer, YReceipt> mActiveReceipts = new HashMap<Integer, YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();
	private NotificationManager mNM;
	@SuppressWarnings("unused")
	private YLog mLog;
	private int mReceiptID = 0;
	private YSecurity mSecurity;

	@Override
	public void onCreate() {
		super.onCreate();
		mManager = new AvailableRecipesManager(this);
		mSecurity = new YSecurity(this);
		mLog = new YLog(this);
		Log.d("LIFECYCLE", this.toString() + " onCreate");
		ReceiptsDatabaseHelper dbHelper = new ReceiptsDatabaseHelper(this);
		mReceiptID = dbHelper.getMaxId();
		List<ReceiptFromDatabase> activatedReceipts = dbHelper.getActivatedReceipts();
		for (ReceiptFromDatabase receiptFromDatabase : activatedReceipts) {
			reviveReceipt(receiptFromDatabase);
		}
		Log.d("RECEIPTS_DB", mReceiptID + " init");

		registerLogUtilsReceiver();
		registerReceiptsUtilsReceiver();
		registerLoginReceiver();
		showNotification();
	}

	private int reviveReceipt(ReceiptFromDatabase receiptFromDatabase) {
		YReceipt receipt = mManager.get(receiptFromDatabase.name).newInstance();
		long feats = receipt.requestFeatures();
		YFeatureList features = new YFeatureList(feats);
		initFeatures(features);
		receiptFromDatabase.yParams.setFeatures(feats);
		receipt.initialize(this, receiptFromDatabase.yParams, features, receiptFromDatabase.id,
				receiptFromDatabase.timestamp);
		for (Entry<Long, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterReceipt: " + receipt.getName() + " to " + entry.getKey());
			entry.getValue().registerReceipt(receipt);
		}
		mActiveReceipts.put(receiptFromDatabase.id, receipt);
		showNotification();
		return receiptFromDatabase.id;
	}

	private void registerReceiptsUtilsReceiver() {
		IntentFilter f = new IntentFilter(ACTION_ACTIVATE_RECEIPT);
		BroadcastReceiver b = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String name = "";
				name = intent.getStringExtra(RECEIPT);
				Log.d("SERVICE", "EnableReceipt: " + name);
				YParamList params = intent.getParcelableExtra(PARAMS);
				enableReceipt(name, params);
			}
		};
		registerReceiver(b, f);

		IntentFilter activeReceiptsIntentFilter = new IntentFilter(ACTION_GET_RECEIPTS_REQUEST);
		BroadcastReceiver activeReceiptsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Intent i = new Intent();
				ArrayList<ActiveReceiptInfo> activeReceiptInfos = new ArrayList<ActiveReceiptInfo>();
				for (Entry<Integer, YReceipt> receipt : mActiveReceipts.entrySet()) {
					ActiveReceiptInfo activeReceiptInfo = new ActiveReceiptInfo(receipt.getValue().getName(), receipt
							.getValue().getParams(), receipt.getKey());
					activeReceiptInfos.add(activeReceiptInfo);
				}
				i.putParcelableArrayListExtra(RECEIPT_INFOS, activeReceiptInfos);
				i.setAction(ACTION_GET_RECEIPTS_RESPONSE);
				sendBroadcast(i);
			}
		};
		registerReceiver(activeReceiptsReceiver, activeReceiptsIntentFilter);
		BroadcastReceiver unregisterReceiptReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				int id = intent.getIntExtra(RECEIPT_ID, -1);
				if (id != -1) {
					disableReceipt(id);

					Intent i = new Intent();
					ArrayList<ActiveReceiptInfo> activeReceiptInfos = new ArrayList<ActiveReceiptInfo>();
					for (Entry<Integer, YReceipt> receipt : mActiveReceipts.entrySet()) {
						ActiveReceiptInfo activeReceiptInfo = new ActiveReceiptInfo(receipt.getValue().getName(),
								receipt.getValue().getParams(), receipt.getKey());
						activeReceiptInfos.add(activeReceiptInfo);
					}
					i.putParcelableArrayListExtra(RECEIPT_INFOS, activeReceiptInfos);
					i.setAction(ACTION_GET_RECEIPTS_RESPONSE);
					sendBroadcast(i);
				}
			}
		};
		IntentFilter unregisterFilter = new IntentFilter();
		unregisterFilter.addAction(ACTION_DEACTIVATE_RECEIPT);
		registerReceiver(unregisterReceiptReceiver, unregisterFilter);

		BroadcastReceiver getAvailableReceiptsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				mManager.refresh();
				Intent i = new Intent(AVAILABLE_RESPONSE);
				i.putExtra(AVAILABLE_RECEIPTS, getAvaibleRecipesBundle());
				sendBroadcast(i);
			}
		};
		IntentFilter availableFilter = new IntentFilter(AVAILABLE_REQUEST);
		registerReceiver(getAvailableReceiptsReceiver, availableFilter);

		BroadcastReceiver getLogsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				mManager.refresh();
				String tag = intent.getStringExtra(RECEIPT_TAG);
				sendLogs(tag);
			}
		};
		IntentFilter getLogsFilter = new IntentFilter(AVAILABLE_REQUEST);
		registerReceiver(getLogsReceiver, getLogsFilter);

		BroadcastReceiver sendTextReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ActiveReceiptInfo info = intent.getParcelableExtra(InitializedReceiptDialog.INFO);
				String text = intent.getStringExtra(InitializedReceiptDialog.TEXT);
				YLog.d("SERVICE", "Text to recipe" + info.getId());
				mActiveReceipts.get(info.getId()).handleEvent(new YTextEvent(text));
			}
		};
		IntentFilter sendTextFilter = new IntentFilter(ACTION_SEND_TEXT);
		registerReceiver(sendTextReceiver, sendTextFilter);
	}

	public void sendLogs(String tag) {
		if (tag != null) {
			Log.d("SendLogs", "" + tag);
			Intent i = new Intent(ACTION_RECEIPT_LOGS_RESPONSE);
			i.putExtra(RECEIPT_TAG, tag);
			i.putExtra(RECEIPT_LOGS, YLog.getFilteredHistory(tag));
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
				mSecurity.login(username, password, YReceiptsService.this);
			}
		};
		registerReceiver(b, f);

		IntentFilter f2 = new IntentFilter(ACTION_LOGOUT);
		BroadcastReceiver b2 = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				mSecurity.logout();
				List<Integer> toDisable = new ArrayList<Integer>();
				for (Entry<Integer, YReceipt> e : mActiveReceipts.entrySet()) {
					YReceipt rec = e.getValue();
					if ((rec.requestFeatures() & Y.Group) != 0)
						toDisable.add(e.getKey());
				}
				for (Integer id : toDisable) {
					disableReceipt(id);
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
		int active = mActiveReceipts.size();
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

		Notification notification = new Notification(icon, text, System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				InitializedReceipesActivity.class), 0);

		notification.setLatestEventInfo(this, text, "Active receipts: " + active, contentIntent);

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNM.notify(NOTIFICATION, notification);
	}

	public Bundle getAvaibleRecipesBundle() {
		Bundle b = new Bundle();
		for (Entry<String, YReceipt> entry : mManager.getAvailableReceipesMap().entrySet()) {
			String receiptName = entry.getKey();
			YParamList params = new YParamList();
			params.setFeatures(entry.getValue().requestFeatures());
			entry.getValue().requestParams(params);
			b.putParcelable(receiptName, params);
		}
		return b;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("LIFECYCLE", this.toString() + " onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public int enableReceipt(String name, YParamList params) {
		int id = ++mReceiptID;
		Log.d("RECEIPTS_DB", id + "");
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		YReceipt receipt = mManager.get(name).newInstance();
		long feats = receipt.requestFeatures();
		Log.d("SERVICE", "Feats: " + Long.toHexString(feats));
		YFeatureList features = new YFeatureList(feats);
		initFeatures(features);
		params.setFeatures(feats);
		receipt.initialize(this, params, features, id, timestamp);
		for (Entry<Long, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterReceipt: " + receipt.getName() + " to " + entry.getKey());
			entry.getValue().registerReceipt(receipt);
		}
		YLog.d("SERVICE", "ActivateReceipt: " + receipt.getName() + " ,ID: " + id);
		mActiveReceipts.put(id, receipt);
		showNotification();
		ReceiptsDatabaseHelper receiptsHelper = new ReceiptsDatabaseHelper(this);
		receiptsHelper.saveReceipt(receipt, id);
		return id;
	}

	private void initFeatures(YFeatureList features) {
		Log.i("SERVICE", "Initializing feats");
		for (Entry<Long, YFeature> entry : features) {
			Long featId = entry.getKey();
			YFeature feat = mActiveFeatures.get(featId);
			Log.i("SERVICE", "initializing" + Long.toHexString(featId));
			if (feat != null) {
				Log.i("SERVICE", Long.toHexString(feat.getId()) + "already initialized");
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
	public void disableReceipt(Integer id) {
		YReceipt receipt = getActiveReceipts().get(id);
		List<Long> toDelete = new ArrayList<Long>();
		for (Entry<Long, YFeature> entry : receipt.getFeatures()) {
			YFeature feat = entry.getValue();
			YLog.d("SERVICE", "UnregisterReceipt: " + receipt.getName() + " from " + entry.getKey());
			feat.unregisterReceipt(receipt);
			if (!feat.isUsed()) {
				toDelete.add(entry.getKey());
				YLog.d("SERVICE", "UninitializeFeature: " + feat.getId());
				feat.uninitialize();
			}
		}
		mActiveFeatures.removeAll(toDelete);
		ReceiptsDatabaseHelper receiptsHelper = new ReceiptsDatabaseHelper(this);
		receiptsHelper.removeReceipt(id);
		YLog.d("SERVICE", "DeactivateReceipt: " + receipt.getName() + " ,ID: " + id);
		mActiveReceipts.remove(id);
		showNotification();
	}

	private Map<Integer, YReceipt> getActiveReceipts() {
		return Collections.unmodifiableMap(mActiveReceipts);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNM.cancel(NOTIFICATION);
	}

	@Override
	public Context getContext() {
		return this;
	}

	public AvailableRecipesManager getAvaibleRecipesManager() {
		return mManager;
	}

	public void updateAvailableReceipts() {
		mManager.refresh();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YSecurity getSecurity() {
		return mSecurity;
	}
}