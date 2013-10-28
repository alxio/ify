package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.Context;
import android.net.wifi.WifiManager;

public class YWifiFeature extends YFeature {
	public static final int ID = Y.Wifi;
	public static final String NAME = "YWifi";

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private WifiManager mManager;

	@Override
	public void init(YReceiptsService srv) {
		mManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	}

	public void enable() {
		mManager.setWifiEnabled(true);
	}

	public void disable(){
		mManager.setWifiEnabled(false);
	}

	public boolean isEnabled() {
		return mManager.isWifiEnabled();
	}

	@Override
	public void uninitialize() {
		// TODO Auto-generated method stub

	}
}
