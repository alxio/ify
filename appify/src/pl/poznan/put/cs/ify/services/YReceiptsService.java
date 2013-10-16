package pl.poznan.put.cs.ify.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

//W sumie nie wiem, czy to siê przyda czy nie, chcia³em jakiœ serwis wrzuciæ i nie wiem co dalej.
public class YReceiptsService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("LIFECYCLE", this.toString() + " onCreate");
		IntentFilter f = new IntentFilter("IFY");
		BroadcastReceiver b = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d("SERVICE", this.toString() + " received broadcast");
			}
		};
		registerReceiver(b,f);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("LIFECYCLE", this.toString() + " onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	private Map<Integer, YReceipt> mActiveReceipts = new HashMap<Integer, YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();

	public int enableReceipt(YReceipt receipt, YParamList params) {
		YFeatureList features = new YFeatureList();
		receipt.requestFeatures(features);
		initFeatures(features);
		receipt.initialize(params, features);
		for (Entry<String, YFeature> entry : features) {
			entry.getValue().registerReceipt(receipt);
		}
		int time = (int) (System.currentTimeMillis() / 1000);
		getActiveReceipts().put(time, receipt);
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
				feat.initialize(this, this);
			}
		}
	}

	public void disableReceipt(Integer id) {
		YReceipt receipt = getActiveReceipts().get(id);
		List<String> toDelete = new ArrayList<String>();
		for (Entry<String, YFeature> entry : receipt.getFeatures()) {
			YFeature feat = entry.getValue();
			feat.removeUser(receipt);
			if (!feat.isUsed()) {
				toDelete.add(entry.getKey());
				feat.uninitialize();
			}
		}
		mActiveFeatures.removeAll(toDelete);
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