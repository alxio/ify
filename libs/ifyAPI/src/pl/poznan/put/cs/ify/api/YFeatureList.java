package pl.poznan.put.cs.ify.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YAudioManagerFeature;
import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YCallsFeature;
import pl.poznan.put.cs.ify.api.features.YGPSFeature;
import pl.poznan.put.cs.ify.api.features.YGeocoderFeature;
import pl.poznan.put.cs.ify.api.features.YInternetFeature;
import pl.poznan.put.cs.ify.api.features.YNotificationFeature;
import pl.poznan.put.cs.ify.api.features.YRawPlayerFeature;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.YSoundFeature;
import pl.poznan.put.cs.ify.api.features.YTimeFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import android.util.Log;

public class YFeatureList implements Iterable<Map.Entry<Long, YFeature>> {
	private HashMap<Long, YFeature> mFeatures = new HashMap<Long, YFeature>();

	public YFeatureList() {
	}

	/**
	 * Creates a list based with given features.
	 * 
	 * @param feats
	 *            bitmask with feature ID's
	 */
	public YFeatureList(long feats) {
		for (long i = 1; i <= feats; i <<= 1) {
			if ((feats & i) != 0) {
				YFeature feat = Y.getFeature(i);
				Log.d("Feature", Long.toHexString(i) + feat);
				if (feat != null)
					mFeatures.put(i, Y.getFeature(i));
			}
		}
	}

	/**
	 * Converts bitmask to String with feature names
	 * 
	 * @param feats
	 *            bitmask with feature ID's
	 * @return features names in format "Name1, Name2, ..."
	 */
	public static String maskToString(long feats) {
		StringBuilder sb = new StringBuilder();
		for (long i = 1; i <= feats; i <<= 1) {
			if ((feats & i) != 0) {
				sb.append(Y.getName(i));
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	/**
	 * Adds feature to list
	 * 
	 * @param feature
	 *            to add
	 */
	public void add(YFeature feature) {
		mFeatures.put(feature.getId(), feature);
	}

	/**
	 * Get feature from list by id
	 * 
	 * @param id
	 * @return feature with given id or null
	 */
	public YFeature get(Long id) {
		return mFeatures.get(id);
	}

	@Override
	public Iterator<Entry<Long, YFeature>> iterator() {
		return mFeatures.entrySet().iterator();
	}

	/**
	 * Removes features from list.
	 * 
	 * @param toDelete
	 *            Collection of features to be removed.
	 */
	public void removeAll(Collection<Long> toDelete) {
		for (Long id : toDelete) {
			mFeatures.remove(id);
		}
	}

	/**
	 * Removes single feature from list.
	 * 
	 * @param name
	 *            of feature to remove.
	 */
	public void remove(String name) {
		mFeatures.remove(name);
	}

	/*
	 * Wrappers to features to avoid casting in recipe code below
	 */

	public YWifiFeature getWifi() {
		return (YWifiFeature) get(Y.Wifi);
	}

	public YBatteryFeature getBattery() {
		return (YBatteryFeature) get(Y.Battery);
	}

	public YAccelerometerFeature getAccelerometer() {
		return (YAccelerometerFeature) get(Y.Accelerometer);
	}

	public YSMSFeature getSMS() {
		return (YSMSFeature) get(Y.SMS);
	}

	public YGPSFeature getGPS() {
		return (YGPSFeature) get(Y.GPS);
	}

	public YSoundFeature getSound() {
		return (YSoundFeature) get(Y.Sound);
	}

	public YRawPlayerFeature getRawPlayer() {
		return (YRawPlayerFeature) get(Y.RawPlayer);
	}

	public YGroupFeature getGroup() {
		return (YGroupFeature) get(Y.Group);
	}

	public YGeocoderFeature getGeocoder() {
		return (YGeocoderFeature) get(Y.Geocoder);
	}

	public YTimeFeature getTime() {
		return (YTimeFeature) get(Y.Time);
	}

	public YAudioManagerFeature getAudioManager() {
		return (YAudioManagerFeature) get(Y.AudioManager);
	}

	public YInternetFeature getInternet() {
		return (YInternetFeature) get(Y.Internet);
	}

	public YCallsFeature getCalls() {
		return (YCallsFeature) get(Y.Calls);
	}

	public YNotificationFeature getNotification() {
		return (YNotificationFeature) get(Y.Notification);
	}
}
