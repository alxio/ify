package pl.poznan.put.cs.ify.app;

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
	}

	private void initService() {
		Intent i = new Intent(this, YRecipesService.class);
		startService(i);
	}

	public static Context getContext() {
		return mContext;
	}
}
