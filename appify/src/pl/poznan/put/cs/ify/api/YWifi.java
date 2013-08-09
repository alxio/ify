package pl.poznan.put.cs.ify.api;

import android.content.Context;
import android.net.wifi.WifiManager;

public class YWifi extends YFeature {
	private static WifiManager sManager;
	
	@Override
	public void initialize(Context ctx){
		sManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE); 
	}
	@Override
	public void uninitialize(){
		sManager = null;
	}
	
	private static WifiManager getManager() throws UninitializedException{
		if(sManager == null)throw new UninitializedException();
		return sManager;
	}

	public static void enable() throws UninitializedException{
		getManager().setWifiEnabled(true);
	}
	public static void disable() throws UninitializedException{
		getManager().setWifiEnabled(false);
	}
	public static boolean isEnabled() throws UninitializedException{
		return getManager().isWifiEnabled();
	}
}
