package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.features.YShortcutFeature;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ShortcutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent received = getIntent();
		Intent broadcast = new Intent(YShortcutFeature.SHORTCUT_ACTION);
		broadcast.putExtras(received);
		sendBroadcast(broadcast);
		moveTaskToBack(true);
		finish();
	}
}
