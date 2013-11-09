package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class YBatteryFeature extends YFeature {
	public static final int ID = Y.Battery;
	public static final String NAME = "YBatteryFeature";

	@Override
	public int getId() {
		return ID;
	}

	private int mLevel = -1;
	private BroadcastReceiver mBatInfoReceiver;

	public int getLastLevel() {
		return mLevel;
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mBatInfoReceiver);
	}

	@Override
	public void init(IYReceiptHost srv) {
		mBatInfoReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent intent) {
				mLevel = intent.getIntExtra("level", 0);
				sendNotification(new YBatteryEvent(mLevel));
			}
		};
		mContext.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

}
