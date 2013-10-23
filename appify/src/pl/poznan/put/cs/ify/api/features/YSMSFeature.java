package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class YSMSFeature extends YFeature {
	public static final int ID = Y.SMS;
	public static final String NAME = "YSMSFeature";
	@Override
	public int getId() {
		return ID;
	}
	@Override
	public String getName() {
		return NAME;
	}
	
	private BroadcastReceiver mSMSReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			sendNotification(intent.getExtras());
		}
	};

	@Override
	public void init(YReceiptsService srv) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		mContext.registerReceiver(mSMSReceiver, intentFilter);
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mSMSReceiver);
	}

	public void sendSMS(String phoneNumber, String message) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
