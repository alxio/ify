package pl.poznan.put.cs.ify.android.receivers;

import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class YBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent coreServiceIntent = new Intent(context, YReceiptsService.class);
		context.startService(coreServiceIntent);
	}

}
