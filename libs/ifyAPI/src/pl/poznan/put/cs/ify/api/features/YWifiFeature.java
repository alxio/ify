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
package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent.WiFi_EventType;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class YWifiFeature extends YFeature {

	/**
	 * Receiver handling changes in WiFi state. Reacts to enabling and disabling
	 * WiFi by sending YWifiEvent with proper type.
	 */
	private BroadcastReceiver mConnectionStateChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			YWifiEvent event = null;
			if (intent.getAction()
					.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
				int wifiState = intent.getIntExtra(
						WifiManager.EXTRA_WIFI_STATE, -1);
				if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
					event = new YWifiEvent(WiFi_EventType.WIFI_ENABLED);
				} else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
					event = new YWifiEvent(WiFi_EventType.WIFI_DISABLED);
				}
			}
			if (event != null) {
				sendNotification(event);
			}
		}
	};

	/**
	 * Receiver handling changes in connection state. Reacts to establishing and
	 * loosing connection by sending YWifiEvent with proper type.
	 */
	private BroadcastReceiver mWifiStateChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
				NetworkInfo current = intent
						.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				YWifiEvent event = new YWifiEvent(current.getState());
				if (current.isConnected()) {
					WifiInfo wifiInfo = intent
							.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
					event.setSSID(wifiInfo.getSSID());
				}
				if (event != null) {
					sendNotification(event);
				}
			}

		}
	};

	public static final long ID = Y.Wifi;
	public static final String NAME = "YWifi";

	@Override
	public long getId() {
		return ID;
	}

	private WifiManager mManager;

	private Context mContext;

	/**
	 * Registers broadcast receivers reacting to
	 * WifiManager.WIFI_STATE_CHANGED_ACTION and
	 * WifiManager.NETWORK_STATE_CHANGED_ACTIOn
	 */
	@Override
	public void init(IYRecipeHost srv) {
		mManager = (WifiManager) mHost.getContext().getSystemService(
				Context.WIFI_SERVICE);
		IntentFilter networkStateChangedFilter = new IntentFilter();
		networkStateChangedFilter
				.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		mContext = srv.getContext();
		mContext.registerReceiver(mConnectionStateChangedReceiver,
				networkStateChangedFilter);

		IntentFilter wifi = new IntentFilter();
		wifi.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		mContext.registerReceiver(mWifiStateChangedReceiver,
				networkStateChangedFilter);
	}

	/**
	 * Enables WiFi.
	 */
	public void enable() {
		mManager.setWifiEnabled(true);
	}

	/**
	 * Disables WiFi.
	 */
	public void disable() {
		mManager.setWifiEnabled(false);
	}

	/**
	 * Checks if WiFi is enabled.
	 */
	public boolean isEnabled() {
		return mManager.isWifiEnabled();
	}

	/**
	 * @return SSID of current network or null if not connected
	 */
	public String getSSID() {
		WifiInfo info = mManager.getConnectionInfo();
		if (info != null)
			return info.getBSSID();
		return null;
	}

	/**
	 * Unregister broadcast receivers.
	 */
	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mConnectionStateChangedReceiver);
		mContext.unregisterReceiver(mWifiStateChangedReceiver);
	}
}
