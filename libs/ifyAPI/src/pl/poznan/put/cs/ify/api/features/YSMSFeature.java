package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YSMSEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class YSMSFeature extends YFeature {
	public static final long ID = Y.SMS;
	public static final String NAME = "YSMSFeature";

	@Override
	public long getId() {
		return ID;
	}

	private BroadcastReceiver mSMSReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			sendNotification(new YSMSEvent(intent));
		}
	};

	@Override
	public void init(IYRecipeHost srv) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		mHost.getContext().registerReceiver(mSMSReceiver, intentFilter);
	}

	@Override
	public void uninitialize() {
		mHost.getContext().unregisterReceiver(mSMSReceiver);
	}

	public void sendSMS(String phoneNumber, String message) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
