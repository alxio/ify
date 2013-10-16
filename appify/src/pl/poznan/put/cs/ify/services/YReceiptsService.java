package pl.poznan.put.cs.ify.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.prototype.AvailableRecipesManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class YReceiptsService extends Service {
	public static final String PARAMS = "pl.poznan.put.cs.ify.PARAMS";
	public static final String RECEIPT = "pl.poznan.put.cs.ify.RECEIPT";
	public static final String INTENT = "pl.poznan.put.cs.ify.INTENT";

	private AvailableRecipesManager mManager = new AvailableRecipesManager();
	private Map<Integer, YReceipt> mActiveReceipts = new HashMap<Integer, YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("LIFECYCLE", this.toString() + " onCreate");
		IntentFilter f = new IntentFilter(INTENT);
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
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("LIFECYCLE", this.toString() + " onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

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
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}