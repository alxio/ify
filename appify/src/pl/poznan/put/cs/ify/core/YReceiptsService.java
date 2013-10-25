package pl.poznan.put.cs.ify.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.RecipesListActivity;
import pl.poznan.put.cs.ify.appify.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class YReceiptsService extends Service {
	public static final String PARAMS = "pl.poznan.put.cs.ify.PARAMS";
	public static final String RECEIPT = "pl.poznan.put.cs.ify.RECEIPT";
	public static final String RECEIPT_INFOS = "pl.poznan.put.cs.ify.RECEIPT_INFOS";
	public static final String INTENT = "pl.poznan.put.cs.ify.INTENT";
	public static final String ACTION_GET_RECEIPTS_REQUEST = "pl.poznan.put.cs.ify.ACTION_GET_RECEIPTS_REQ";
	public static final String ACTION_GET_RECEIPTS_RESPONSE = "pl.poznan.put.cs.ify.ACTION_GET_RECEIPTS_RESP";
	public static final String ACTION_DEACTIVATE_RECEIPT = "pl.poznan.put.cs.ify.ACTION_DEACTIVATE_RECEIPT";
	public static final String RECEIPT_ID = "pl.poznan.put.cs.ify.RECEIPT_ID";

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
		YLog.d("LIFECYCLE", this.toString() + " onCreate");
		IntentFilter f = new IntentFilter(INTENT);
		BroadcastReceiver b = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String name = "";
				name = intent.getStringExtra(RECEIPT);
				YLog.d("SERVICE", "EnableReceipt: " + name);
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
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		showNotification();

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

	private void showNotification() {
		CharSequence text = getText(R.string.app_name);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.app2, text, System.currentTimeMillis());

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
		PendingIntent contentIntent = PendingIntent
				.getActivity(this, 0, new Intent(this, RecipesListActivity.class), 0);

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
		YLog.d("LIFECYCLE", this.toString() + " onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	public int enableReceipt(String name, YParamList params) {
		YReceipt receipt = mManager.get(name).newInstance();
		YFeatureList features = new YFeatureList();
		receipt.requestFeatures(features);
		initFeatures(features);
		receipt.initialize(params, features);
		for (Entry<String, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterReceipt: " + receipt.getName() + " to " + entry.getKey());
			entry.getValue().registerReceipt(receipt);
		}
		int time = (int) (System.currentTimeMillis() / 1000);
		YLog.d("SERVICE", "ActivateReceipt: " + receipt.getName() + " ,ID: " + time);
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
				YLog.d("SERVICE", "InitializeFeature: " + feat.getName());
				feat.initialize(this, this);
				mActiveFeatures.add(feat);
			}
		}
	}

	public void disableReceipt(Integer id) {
		YReceipt receipt = getActiveReceipts().get(id);
		List<String> toDelete = new ArrayList<String>();
		for (Entry<String, YFeature> entry : receipt.getFeatures()) {
			YFeature feat = entry.getValue();
			YLog.d("SERVICE", "UnregisterReceipt: " + receipt.getName() + " from " + entry.getKey());
			feat.removeUser(receipt);
			if (!feat.isUsed()) {
				toDelete.add(entry.getKey());
				YLog.d("SERVICE", "UninitializeFeature: " + feat.getName());
				feat.uninitialize();
			}
		}
		mActiveFeatures.removeAll(toDelete);
		YLog.d("SERVICE", "DeactivateReceipt: " + receipt.getName() + " ,ID: " + id);
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

}