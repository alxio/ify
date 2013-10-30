package pl.poznan.put.cs.ify.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.MenuActivity;
import pl.poznan.put.cs.ify.appify.R;
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

public class YReceiptsService extends Service implements IYReceiptHost {
	public static final String PARAMS = "pl.poznan.put.cs.ify.PARAMS";
	public static final String RECEIPT = "pl.poznan.put.cs.ify.RECEIPT";
	public static final String RECEIPT_INFOS = "pl.poznan.put.cs.ify.RECEIPT_INFOS";
	public static final String ACTION_ACTIVATE_RECEIPT = "pl.poznan.put.cs.ify.ACTION_ACTIVATE_RECEIPT";
	public static final String ACTION_GET_RECEIPTS_REQUEST = "pl.poznan.put.cs.ify.ACTION_GET_RECEIPTS_REQ";
	public static final String ACTION_GET_RECEIPTS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_GET_RECEIPTS_RESP";
	public static final String ACTION_DEACTIVATE_RECEIPT = "pl.poznan.put.cs.ify.ACTION_DEACTIVATE_RECEIPT";
	public static final String RECEIPT_ID = "pl.poznan.put.cs.ify.RECEIPT_ID";
	public static final String TOGGLE_LOG = "pl.poznan.put.cs.ify.TOGGLE_LOG";

	private int NOTIFICATION = R.string.app_name;

	private AvailableRecipesManager mManager;
	private Map<Integer, YReceipt> mActiveReceipts = new HashMap<Integer, YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();
	private NotificationManager mNM;
	private YLog mLog;

	@Override
	public void onCreate() {
		super.onCreate();
		mManager = new AvailableRecipesManager(this);
		mLog = new YLog(this);
		Log.d("LIFECYCLE", this.toString() + " onCreate");

		registerLogUtilsReceiver();
		registerReceiptsUtilsReceiver();
		showNotification();
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

	private void showNotification() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		CharSequence text = getText(R.string.app_name);
		Notification notification = new Notification(R.drawable.ify, text, System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this
		// notification
		// PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		// new Intent(this, LocalServiceActivities.Controller.class), 0);
		//
		// // Set the info for the views that show in the notification panel.
		// notification.setLatestEventInfo(this,
		// getText(R.string.local_service_label),
		// text, contentIntent);

		// Send the notification.
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MenuActivity.class), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, getText(R.string.app_name), text, contentIntent);
		mNM.notify(NOTIFICATION, notification);

	}

	public Bundle getAvaibleRecipesBundle() {
		Bundle b = new Bundle();
		for (Entry<Integer, YReceipt> entry : mActiveReceipts.entrySet()) {
			b.putParcelable(entry.getValue().getName(), entry.getValue().getParams());
		}
		return b;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("LIFECYCLE", this.toString() + " onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.poznan.put.cs.ify.core.YInterface#enableReceipt(java.lang.String,
	 * pl.poznan.put.cs.ify.api.params.YParamList)
	 */
	@Override
	public int enableReceipt(String name, YParamList params) {
		YReceipt receipt = mManager.get(name).newInstance();
		YFeatureList features = new YFeatureList();
		receipt.requestFeatures(features);
		initFeatures(features);
		receipt.initialize(params, features);
		for (Entry<String, YFeature> entry : features) {
			Log.d("SERVICE", "RegisterReceipt: " + receipt.getName() + " to " + entry.getKey());
			entry.getValue().registerReceipt(receipt);
		}
		int time = (int) (System.currentTimeMillis() / 1000);
		Log.d("SERVICE", "ActivateReceipt: " + receipt.getName() + " ,ID: " + time);
		mActiveReceipts.put(time, receipt);
		return time;
	}

	private void initFeatures(YFeatureList features) {
		for (Entry<String, YFeature> entry : features) {
			String featName = entry.getKey();
			YFeature feat = mActiveFeatures.get(featName);
			if (feat != null) {
				entry.setValue(feat);
			} else {
				feat = entry.getValue();
				Log.d("SERVICE", "InitializeFeature: " + feat.getName());
				feat.initialize(this, this);
				mActiveFeatures.add(feat);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.poznan.put.cs.ify.core.YInterface#disableReceipt(java.lang.Integer)
	 */
	@Override
	public void disableReceipt(Integer id) {
		YReceipt receipt = getActiveReceipts().get(id);
		List<String> toDelete = new ArrayList<String>();
		for (Entry<String, YFeature> entry : receipt.getFeatures()) {
			YFeature feat = entry.getValue();
			Log.d("SERVICE", "UnregisterReceipt: " + receipt.getName() + " from " + entry.getKey());
			feat.removeUser(receipt);
			if (!feat.isUsed()) {
				toDelete.add(entry.getKey());
				Log.d("SERVICE", "UninitializeFeature: " + feat.getName());
				feat.uninitialize();
			}
		}
		mActiveFeatures.removeAll(toDelete);
		Log.d("SERVICE", "DeactivateReceipt: " + receipt.getName() + " ,ID: " + id);
		mActiveReceipts.remove(id);
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
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context getContext() {
		return this;
	}

}