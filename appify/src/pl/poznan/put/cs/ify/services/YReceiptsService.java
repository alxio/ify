package pl.poznan.put.cs.ify.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pl.poznan.put.cs.ify.core.YFeature;
import pl.poznan.put.cs.ify.core.YFeatureList;
import pl.poznan.put.cs.ify.core.YReceipt;
import pl.poznan.put.cs.ify.params.YParamList;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.FeatureInfo;

//W sumie nie wiem, czy to siê przyda czy nie, chcia³em jakiœ serwis wrzuciæ i nie wiem co dalej.
public class YReceiptsService extends IntentService {
	private Map<String,YReceipt>mReceipts = new HashMap<String,YReceipt>();
	private YFeatureList mActiveFeatures = new YFeatureList();
	
    public YReceiptsService(String name, YReceipt receipt) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	@Override
    protected void onHandleIntent(Intent workIntent) {
		// TODO Auto-generated constructor stub
    }
	public void enableReceipt(YReceipt receipt, YParamList params){
		YFeatureList features = new YFeatureList();
		receipt.requestFeatures(features);
		initFeatures(features);
		receipt.initialize(params, features);
		for(Entry<String, YFeature> entry : features){
			entry.getValue().registerReceipt(receipt);
		}
		mReceipts.put(receipt.getName(), receipt);
	}
	private void initFeatures(YFeatureList features) {
		for(Entry<String, YFeature> entry : features){
			String featName = entry.getKey();
			YFeature feat = mActiveFeatures.get(featName);
			if(feat != null){
				entry.setValue(feat);
			} else {
				feat = entry.getValue();
				feat.initialize(this, this);
			}
		}
	}
	public void disableReceipt(String name){
		YReceipt receipt = mReceipts.get(name);
		List<String>toDelete = new ArrayList<String>();
		for(Entry<String, YFeature> entry : receipt.getFeatures()){
			YFeature feat = entry.getValue();
			feat.removeUser();
			if(!feat.isUsed()){
				toDelete.add(entry.getKey());
			}
		}
		mActiveFeatures.removeAll(toDelete);
	}
}