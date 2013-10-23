package pl.poznan.put.cs.ify.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;

public class YFeatureList implements Iterable<Map.Entry<String, YFeature>> {
	private HashMap<String, YFeature> mFeatures = new HashMap<String, YFeature>();

	public void add(YFeature feature) {
		mFeatures.put(feature.getName(), feature);
	}

	public YFeature get(String name) {
		return mFeatures.get(name);
	}

	public YWifiFeature getWifi() {
		return (YWifiFeature) get("YWifi");
	}

	public YBatteryFeature getBattery() {
		return (YBatteryFeature) get("YBattery");
	}

	@Override
	public Iterator<Entry<String, YFeature>> iterator() {
		return mFeatures.entrySet().iterator();
	}

	public void removeAll(Collection<String> toDelete) {
		for (String name : toDelete) {
			mFeatures.remove(name);
		}
	}

	public void remove(String name) {
		mFeatures.remove(name);
	}
}
