package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
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
	public void init(IYReceiptHost srv) {
		mManager = (WifiManager) mHost.getContext().getSystemService(Context.WIFI_SERVICE);
	}

	public void enable() {
		mManager.setWifiEnabled(true);
	}

	public void disable() {
		mManager.setWifiEnabled(false);
	}

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
