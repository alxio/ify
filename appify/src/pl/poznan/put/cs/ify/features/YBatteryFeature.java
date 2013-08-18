package pl.poznan.put.cs.ify.features;

import pl.poznan.put.cs.ify.core.YFeature;
import pl.poznan.put.cs.ify.features.triggers.YBatteryTrigger;
import android.content.Context;

public class YBatteryFeature implements YFeature {
	private Context mContext;
	private YBatteryTrigger mTrigger;

	@Override
	public void initialize(Context ctx) {
		mContext = ctx;
		mTrigger = new YBatteryTrigger(ctx);
	}

	@Override
	public String getName() {
		return "YBattery";
	}

	public int getLevel() {
		return mTrigger.getLevel();
	}
}
