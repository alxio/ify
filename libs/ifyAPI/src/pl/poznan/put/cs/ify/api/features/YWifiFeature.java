package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class YWifiFeature extends YFeature {
	public static final long ID = Y.Wifi;
	public static final String NAME = "YWifi";

	@Override
	public long getId() {
		return ID;
	}

	private WifiManager mManager;

	@Override
	public void init(IYRecipeHost srv) {
		mManager = (WifiManager) mHost.getContext().getSystemService(Context.WIFI_SERVICE);
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
		// TODO Auto-generated method stub
	}
}
