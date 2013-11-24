package pl.poznan.put.cs.ify.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;

public class YFeatureList implements Iterable<Map.Entry<Long, YFeature>> {
	private HashMap<Long, YFeature> mFeatures = new HashMap<Long, YFeature>();

	public YFeatureList() {
	}

	public YFeatureList(long feats) {
		for (long i = 1; i <= feats; i <<= 1) {
			if ((feats & i) != 0) {
				YFeature feat = Y.getFeature(i);
				Log.d("Feature",Long.toHexString(i) + feat);
				if (feat != null)
					mFeatures.put(i, Y.getFeature(i));
			}
		}
	}

	public void add(YFeature feature) {
		mFeatures.put(feature.getId(), feature);
	}

	public YFeature get(Long id) {
		return mFeatures.get(id);
	}

	public YWifiFeature getWifi() {
		return (YWifiFeature) get(Y.Wifi);
	}

	public YBatteryFeature getBattery() {
		return (YBatteryFeature) get(Y.Battery);
	}

	@Override
	public Iterator<Entry<Long, YFeature>> iterator() {
		return mFeatures.entrySet().iterator();
	}

	public void removeAll(Collection<Long> toDelete) {
		for (Long id : toDelete) {
			mFeatures.remove(id);
		}
	}

	public void remove(String name) {
		mFeatures.remove(name);
	}
}
