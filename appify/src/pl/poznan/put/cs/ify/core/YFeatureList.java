package pl.poznan.put.cs.ify.core;

import java.util.HashMap;

import pl.poznan.put.cs.ify.features.YWifi;
import pl.poznan.put.cs.ify.features.triggers.YBatteryTrigger;


public class YFeatureList {
	private HashMap<String,YFeature> mFeatures = new HashMap<String,YFeature>();
	public void add(YFeature feature){
		mFeatures.put(feature.getName(), feature);
	}
	public YFeature get(String name){
		return mFeatures.get(name);
	}
	
	public YWifi getWifi(){
		return (YWifi) get("YWifi");
	}
	public YBatteryTrigger getBattery(){
		return (YBatteryTrigger) get("YBattery");
	}
}
