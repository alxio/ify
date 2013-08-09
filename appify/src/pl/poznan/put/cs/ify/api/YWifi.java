package pl.poznan.put.cs.ify.api;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;

public class YWifi {
	private static WifiManager sManager;
	private static WifiManager getManager() throws UninitializedException{
		if(sManager == null)throw new UninitializedException();
		return sManager;
	}
	public static void initialize(Activity a){
		sManager = (WifiManager) a.getSystemService(Context.WIFI_SERVICE); 
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
