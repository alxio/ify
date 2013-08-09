package pl.poznan.put.cs.ify.api.types;

import java.util.HashMap;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YWifi;

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
}
