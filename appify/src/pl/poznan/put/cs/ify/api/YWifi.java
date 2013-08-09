package pl.poznan.put.cs.ify.api;

import android.content.Context;
import android.net.wifi.WifiManager;

public class YWifi extends YFeature {
	private WifiManager mManager;
	@Override
	public String getName() {
		return "YWifi";
	}
	@Override
	public void initialize(Context ctx){
		mManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE); 
	}
	
	private WifiManager getManager() throws UninitializedException{
		if(mManager == null)throw new UninitializedException();
		return mManager;
	}

	public void enable() throws UninitializedException{
		getManager().setWifiEnabled(true);
	}
	public void disable() throws UninitializedException{
		getManager().setWifiEnabled(false);
	}
	public boolean isEnabled() throws UninitializedException{
		return getManager().isWifiEnabled();
	}
}
