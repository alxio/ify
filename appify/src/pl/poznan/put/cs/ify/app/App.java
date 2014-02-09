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
package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class App extends Application {
	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		initService();
		mContext = getApplicationContext();
		initDefaultPreferences();
	}

	private void initService() {
		Intent i = new Intent(this, YRecipesService.class);
		startService(i);
	}

	public static Context getContext() {
		return mContext;
	}

	public void initDefaultPreferences() {
		PreferencesProvider prefs = PreferencesProvider.getInstance(this);
		if (!prefs.getBoolean(PreferencesProvider.KEY_DEFAULTS_SET)) {
			prefs.putString(PreferencesProvider.KEY_SERVER_URL,
					PreferencesProvider.DEFAULT_SERVER_URL);
			prefs.putBoolean(PreferencesProvider.KEY_DEFAULTS_SET, true);
		}
	}
}
