/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Wrapper for SharedPreferences.
 *
 */
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
		if (!getBoolean(PreferencesProvider.KEY_DEFAULTS_SET)) {
			putString(PreferencesProvider.KEY_SERVER_URL,
					PreferencesProvider.DEFAULT_SERVER_URL);
			putBoolean(PreferencesProvider.KEY_DEFAULTS_SET, true);
		}
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
