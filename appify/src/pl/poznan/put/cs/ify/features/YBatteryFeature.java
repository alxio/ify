package pl.poznan.put.cs.ify.features;

import pl.poznan.put.cs.ify.core.YFeature;
import pl.poznan.put.cs.ify.core.YReceipt;
import pl.poznan.put.cs.ify.features.triggers.YBatteryTrigger;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;

public class YBatteryFeature extends YFeature {
	private Context mContext;
	private YReceiptsService mService;
	private YBatteryTrigger mTrigger;

	@Override
	public void initialize(Context ctx, YReceiptsService srv) {
		mContext = ctx;
		mService = srv;
		mTrigger = new YBatteryTrigger(ctx, srv);
	}

	@Override
	public String getName() {
		return "YBattery";
	}

	public int getLevel() {
		return mTrigger.getLevel();
	}

	@Override
	public void registerReceipt(YReceipt receipt) {
		mTrigger.register(receipt);
	}
}
