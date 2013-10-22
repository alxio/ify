package pl.poznan.put.cs.ify.android.receivers;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class YSMSFeature extends YFeature {

	// private YTrigger mSMSReceivedTrigger = new YSMSReceivedTrigger();
	private BroadcastReceiver mSMSReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			trySendNotification(intent.getExtras());
		}
	};

	@Override
	public String getName() {
		return "YSMSFeature";
	}

	@Override
	public void initialize(Context ctx, YReceiptsService srv) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		ctx.registerReceiver(mSMSReceiver, intentFilter);
	}

	// TODO: addContext as parameter
	@Override
	public void uninitialize() {
	}

	public void sendSMS(String phoneNumber, String message) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
