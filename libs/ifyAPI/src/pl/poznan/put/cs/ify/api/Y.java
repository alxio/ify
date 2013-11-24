package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YAudioManagerFeature;
import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YGPSFeature;
import pl.poznan.put.cs.ify.api.features.YGeocoderFeature;
import pl.poznan.put.cs.ify.api.features.YRawPlayer;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.YSoundFeature;
import pl.poznan.put.cs.ify.api.features.YTimeFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;

public class Y {
	public static final long Accelerometer = 0x0001;
	public static final long Battery = 0x0002;
	public static final long SMS = 0x0004;
	public static final long Wifi = 0x0008;
	public static final long GPS = 0x00010;
	public static final long Sound = 0x0020;
	public static final long RawPlayer = 0x0040;
	public static final long Group = 0x0080;
	public static final long Geocoder = 0x0100;
	public static final long Time = 0x0200;
	public static final long AudioManager = 0x0400;
	public static final long Text = 0x0800; // No feature for this, event only

	public static YFeature getFeature(long id) {
		if (id == Accelerometer)
			return new YAccelerometerFeature();
		if (id == Battery)
			return new YBatteryFeature();
		if (id == SMS)
			return new YSMSFeature();
		if (id == Wifi)
			return new YWifiFeature();
		if (id == GPS)
			return new YGPSFeature();
		if (id == Sound)
			return new YSoundFeature();
		if (id == RawPlayer)
			return new YRawPlayer();
		if (id == Group)
			return new YGroupFeature();
		if (id == Geocoder)
			return new YGeocoderFeature();
		if (id == Time)
			return new YTimeFeature();
		if (id == AudioManager)
			return new YAudioManagerFeature();
		return null;
	}
}
