package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.app.Application;
import android.content.Intent;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initService();
	}

	private void initService() {
		Intent i = new Intent(this, YReceiptsService.class);
		startService(i);
	}
}
