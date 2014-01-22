package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class YBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent coreServiceIntent = new Intent(context, YRecipesService.class);
		context.startService(coreServiceIntent);
	}
}