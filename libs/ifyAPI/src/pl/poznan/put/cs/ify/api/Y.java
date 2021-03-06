/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.features.YAccelerometerFeature;
import pl.poznan.put.cs.ify.api.features.YAudioManagerFeature;
import pl.poznan.put.cs.ify.api.features.YBatteryFeature;
import pl.poznan.put.cs.ify.api.features.YCallsFeature;
import pl.poznan.put.cs.ify.api.features.YFilesFeature;
import pl.poznan.put.cs.ify.api.features.YGPSFeature;
import pl.poznan.put.cs.ify.api.features.YGeocoderFeature;
import pl.poznan.put.cs.ify.api.features.YIntentFeature;
import pl.poznan.put.cs.ify.api.features.YInternetFeature;
import pl.poznan.put.cs.ify.api.features.YNotificationFeature;
import pl.poznan.put.cs.ify.api.features.YRawPlayerFeature;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.YShortcutFeature;
import pl.poznan.put.cs.ify.api.features.YSoundFeature;
import pl.poznan.put.cs.ify.api.features.YTextFeature;
import pl.poznan.put.cs.ify.api.features.YTimeFeature;
import pl.poznan.put.cs.ify.api.features.YWifiFeature;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;

/**
 * Represents YFeatures existing in library.
 *
 */
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
	public static final long Text = 0x0800;
	public static final long Internet = 0x1000;
	public static final long Calls = 0x2000;
	public static final long Notification = 0x4000;
	public static final long Files = 0x8000;
	public static final long Intent = 0x10000;
	public static final long Shortcut = 0x20000;

	/**
	 * Returns YFeature object for given id.
	 * @param id
	 */
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
			return new YRawPlayerFeature();
		if (id == Group)
			return new YGroupFeature();
		if (id == Geocoder)
			return new YGeocoderFeature();
		if (id == Time)
			return new YTimeFeature();
		if (id == AudioManager)
			return new YAudioManagerFeature();
		if (id == Internet)
			return new YInternetFeature();
		if (id == Calls)
			return new YCallsFeature();
		if (id == Notification)
			return new YNotificationFeature();
		if (id == Files)
			return new YFilesFeature();
		if (id == Shortcut)
			return new YShortcutFeature();
		if (id == Text)
			return new YTextFeature();
		if (id == Intent)
			return new YIntentFeature();
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return Feature name for given id.
	 */
	public static String getName(long id) {
		if (id == Accelerometer)
			return "Accelerometer";
		if (id == Battery)
			return "Battery";
		if (id == SMS)
			return "SMS";
		if (id == Wifi)
			return "Wifi";
		if (id == GPS)
			return "GPS";
		if (id == Sound)
			return "Sound";
		if (id == RawPlayer)
			return "RawPlayer";
		if (id == Group)
			return "Group";
		if (id == Geocoder)
			return "Geocoder";
		if (id == Time)
			return "Time";
		if (id == AudioManager)
			return "AudioManager";
		if (id == Text)
			return "Text";
		if (id == Internet)
			return "Internet";
		if (id == Calls)
			return "Calls";
		if (id == Notification)
			return "Notification";
		if (id == Files)
			return "Files";
		if (id == Shortcut)
			return "Shortcuts";
		if (id == Intent)
			return "Intent";
		return "";
	}
}
