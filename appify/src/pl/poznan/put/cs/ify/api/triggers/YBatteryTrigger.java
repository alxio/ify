package pl.poznan.put.cs.ify.api.triggers;

import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class YBatteryTrigger extends YTrigger {
	int mLevel = -1;
	private BroadcastReceiver mBatInfoReceiver;
	private Context mContext;

	public YBatteryTrigger(Context ctx, YReceiptsService srv) {
		super();
		mBatInfoReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent intent) {
				mLevel = intent.getIntExtra("level", 0);
				trySendNotification();
			}
		};
		ctx.registerReceiver(this.mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

	}

	public int getLevel() {
		return mLevel;
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mBatInfoReceiver);
	}
}
