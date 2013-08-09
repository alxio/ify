package pl.poznan.put.cs.ify.features;

import java.util.HashMap;


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
