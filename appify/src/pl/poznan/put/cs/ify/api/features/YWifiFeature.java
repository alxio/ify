package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.exceptions.UninitializedException;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.Context;
import android.net.wifi.WifiManager;

public class YWifiFeature extends YFeature {
	public static final int ID = Y.WIFI;
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

	private WifiManager getManager() throws UninitializedException {
		if (mManager == null) {
			throw new UninitializedException("YWifi");
		}
		return mManager;
	}

	public void enable() throws UninitializedException {
		getManager().setWifiEnabled(true);
	}

	public void disable() throws UninitializedException {
		getManager().setWifiEnabled(false);
	}

	public boolean isEnabled() throws UninitializedException {
		return getManager().isWifiEnabled();
	}

	@Override
	public void registerReceipt(YReceipt receipt) {
		// TODO Register in my triggers
	}

	@Override
	public void uninitialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterReceipt(YReceipt receipt) {
		// TODO Auto-generated method stub

	}
}
