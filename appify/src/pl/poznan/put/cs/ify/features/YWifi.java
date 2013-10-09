package pl.poznan.put.cs.ify.features;

import pl.poznan.put.cs.ify.core.UninitializedException;
import pl.poznan.put.cs.ify.core.YFeature;
import pl.poznan.put.cs.ify.core.YReceipt;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;
import android.net.wifi.WifiManager;

public class YWifi extends YFeature {
	private WifiManager mManager;

	@Override
	public String getName() {
		return "YWifi";
	}

	@Override
	public void initialize(Context ctx, YReceiptsService srv) {
		mManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
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
}
