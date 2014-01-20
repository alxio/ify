package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YBatteryEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class YBatteryFeature extends YFeature {
	public static final long ID = Y.Battery;
	public static final String NAME = "YBatteryFeature";

	@Override
	public long getId() {
		return ID;
	}

	private int mLevel = -1;
	private BroadcastReceiver mBatInfoReceiver;

	/**
	 * @return last received battery level percentage.
	 */
	public int getLastLevel() {
		return mLevel;
	}

	@Override
	public void uninitialize() {
		mHost.getContext().unregisterReceiver(mBatInfoReceiver);
	}

	@Override
	public void init(IYRecipeHost srv) {
		mBatInfoReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent intent) {
				mLevel = intent.getIntExtra("level", 0);
				sendNotification(new YBatteryEvent(mLevel));
			}
		};
		mHost.getContext().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

}
