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
import pl.poznan.put.cs.ify.api.features.YUserData;
import pl.poznan.put.cs.ify.api.group.YCommData;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.app.InitializedReceipesActivity;
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
	public static final String AVAILABLE_RESPONSE = "pl.poznan.put.cs.ify.AVAILABLE_RESPONSE";
	public static final String AVAILABLE_REQUEST = "pl.poznan.put.cs.ify.AVAILABLE_REQUEST";
	public static final String AVAILABLE_RECEIPTS = "pl.poznan.put.cs.ify.AVAILABLE_RECEIPTS";
	public static final String TOGGLE_LOG = "pl.poznan.put.cs.ify.TOGGLE_LOG";

	private int NOTIFICATION = R.string.app_name;

	private AvailableRecipesManager mManager;
	private Map<Integer, YReceipt> mActiveReceipts = new HashMap<Integer, YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();
	private NotificationManager mNM;
	private YLog mLog;
	private int mReceiptID = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		mManager = new AvailableRecipesManager(this);
		mLog = new YLog(this);
		Log.d("LIFECYCLE", this.toString() + " onCreate");

		registerLogUtilsReceiver();
		registerReceiptsUtilsReceiver();
		showNotification();

		YCommData com = new YCommData(12, "scony@htcEvo", new YUserData("BadumRecipe", "alx", "motorola", "ify"));
		com.add("tel", YParamType.String, "+48121523");
		com.add("fax", YParamType.String, "+184221523");
		com.add("gay", YParamType.Boolean, false);
		com.add("null", null);
		com.add("id", YParamType.Integer, 666);
		String json = com.toJson();
		YCommData com2 = YCommData.fromJson(json);
		YLog.wtf("COMMDATA", com2.toJson());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.poznan.put.cs.ify.core.YInterface#enableReceipt(java.lang.String,
	 * pl.poznan.put.cs.ify.api.params.YParamList)
	 */
	@Override
	public int enableReceipt(String name, YParamList params) {
		int id = ++mReceiptID;
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		YReceipt receipt = mManager.get(name).newInstance();
		YFeatureList features = new YFeatureList();
		receipt.requestFeatures(features);
		initFeatures(features);
		receipt.initialize(params, features, id, timestamp);
		for (Entry<Integer, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterReceipt: " + receipt.getName() + " to " + entry.getKey());
			entry.getValue().registerReceipt(receipt);
		}
		YLog.d("SERVICE", "ActivateReceipt: " + receipt.getName() + " ,ID: " + id);
		mActiveReceipts.put(id, receipt);
		showNotification();
		return id;
	}

	private void initFeatures(YFeatureList features) {
		for (Entry<Integer, YFeature> entry : features) {
			Integer featId = entry.getKey();
			YFeature feat = mActiveFeatures.get(featId);
			if (feat != null) {
				entry.setValue(feat);
			} else {
				feat = entry.getValue();
				YLog.d("SERVICE", "InitializeFeature: " + feat.getId());
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
		List<Integer> toDelete = new ArrayList<Integer>();
		for (Entry<Integer, YFeature> entry : receipt.getFeatures()) {
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
}