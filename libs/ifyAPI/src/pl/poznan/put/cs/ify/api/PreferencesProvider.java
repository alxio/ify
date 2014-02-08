package pl.poznan.put.cs.ify.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesProvider {

	private static final String SHARED_PREFS_NAME = "pl.poznan.put.cs.ify";
	public static final String DEFAULT_STRING = "";
	public static final String DEFAULT_SERVER_URL = "http://ify.cs.put.poznan.pl/WebIFY-1.0/rest/";
	public static final String KEY_SERVER_URL = "KEY_SERVER_URL";
	public static final String KEY_DEFAULTS_SET = "KEY_DEFAULTS_SET";
	public static final String KEY_LOGGED = "KEY_LOGGED";
	public static final String KEY_HASH = "KEY_HASH";
	public static final String KEY_USERNAME = "KEY_USERNAME";

	private static final boolean DEFAULT_BOOLEAN = false;

	private SharedPreferences mPrefs;

	public PreferencesProvider(Context c) {
		int mode;
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			mode = Context.MODE_MULTI_PROCESS;
		} else {
			mode = Context.MODE_PRIVATE;
		}
		mPrefs = c.getSharedPreferences(SHARED_PREFS_NAME, mode);
	}

	public static PreferencesProvider getInstance(Context c) {
		return new PreferencesProvider(c);
	}

	public void putString(String key, String value) {
		Editor edit = mPrefs.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public String getString(String key) {
		return mPrefs.getString(key, DEFAULT_STRING);
	}

	public boolean getBoolean(String key) {
		return mPrefs.getBoolean(key, DEFAULT_BOOLEAN);
	}

	public void putBoolean(String key, boolean b) {
		Editor edit = mPrefs.edit();
		edit.putBoolean(key, b);
		edit.commit();
	}
}
