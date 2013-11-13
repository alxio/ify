package pl.poznan.put.cs.ify.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;

public class YFeatureList implements Iterable<Map.Entry<Integer, YFeature>> {
	private HashMap<Integer, YFeature> mFeatures = new HashMap<Integer, YFeature>();

	public void add(YFeature feature) {
		mFeatures.put(feature.getId(), feature);
	}

	public YFeature get(Integer id) {
		return mFeatures.get(id);
	}

	public YWifiFeature getWifi() {
		return (YWifiFeature) get(Y.Wifi);
	}

	public YBatteryFeature getBattery() {
		return (YBatteryFeature) get(Y.Battery);
	}

	@Override
	public Iterator<Entry<Integer, YFeature>> iterator() {
		return mFeatures.entrySet().iterator();
	}

	public void removeAll(Collection<Integer> toDelete) {
		for (Integer id : toDelete) {
			mFeatures.remove(id);
		}
	}

	public void remove(String name) {
		mFeatures.remove(name);
	}
}
