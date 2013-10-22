package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;

public class YBatteryFeature extends YFeature {
	private Context mContext;
	private YReceiptsService mService;
//	private YBatteryTrigger mBatteryTrigger;

	@Override
	public void initialize(Context ctx, YReceiptsService srv) {
		mContext = ctx;
		mService = srv;
		// mBatteryTrigger = new YBatteryTrigger(ctx, srv);
	}

	@Override
	public String getName() {
		return "YBattery";
	}

	// public int getLevel() {
	// // return mBatteryTrigger.getLevel();
	// }

	@Override
	public void registerReceipt(YReceipt receipt) {
		// mBatteryTrigger.register(receipt);
	}

	@Override
	public void uninitialize() {
		// mBatteryTrigger.uninitialize();
	}

	@Override
	public void unregisterReceipt(YReceipt receipt) {
		// mBatteryTrigger.unregister(receipt);
	}
}
