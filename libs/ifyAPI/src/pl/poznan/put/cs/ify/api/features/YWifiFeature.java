package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent.EventType;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class YWifiFeature extends YFeature {

	private BroadcastReceiver mConnectionStateChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			YWifiEvent event = null;
			if (intent.getAction()
					.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
				int wifiState = intent.getIntExtra(
						WifiManager.EXTRA_WIFI_STATE, -1);
				if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
					event = new YWifiEvent(EventType.WIFI_ENABLED);
				} else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
					event = new YWifiEvent(EventType.WIFI_DISABLED);
				}
			} else if (intent.getAction().equals(
					WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
				NetworkInfo current = intent
						.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if (current.isConnected()) {
					event = new YWifiEvent(EventType.CONNECTION_ESTABLISHED);
					WifiInfo wifiInfo = intent
							.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
					event.setSSID(wifiInfo.getSSID());
				} else {
					event = new YWifiEvent(EventType.CONNECTION_LOST);
				}

			}
			if (event != null) {
				sendNotification(event);
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

	@Override
	public void init(IYRecipeHost srv) {
		mManager = (WifiManager) mHost.getContext().getSystemService(
				Context.WIFI_SERVICE);
		IntentFilter f = new IntentFilter();
		f.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		f.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		mContext = srv.getContext();
		mContext.registerReceiver(mConnectionStateChangedReceiver, f);
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

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mConnectionStateChangedReceiver);
	}
}
