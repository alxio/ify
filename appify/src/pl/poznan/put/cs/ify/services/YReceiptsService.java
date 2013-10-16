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
import pl.poznan.put.cs.ify.prototype.AvailableRecipesManager;
import android.app.IntentService;
import android.content.Intent;

//W sumie nie wiem, czy to siê przyda czy nie, chcia³em jakiœ serwis wrzuciæ i nie wiem co dalej.
public class YReceiptsService extends IntentService {
	private AvailableRecipesManager mManager;
	private Map<Integer, YReceipt> mActiveReceipts = new HashMap<Integer, YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();

	public YReceiptsService(String name, YReceipt receipt) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		// TODO Auto-generated constructor stub
	}

	public int enableReceipt(String name, YParamList params) {
		YReceipt receipt = mManager.get(name).newInstance();
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
}