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
