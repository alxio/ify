package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class YSMSEvent extends YEvent {
	public static final long ID = Y.SMS;
	private String mMessage;
	private String mSender;

	/**
	 * Might be deprecated soon, check {@see <a href=
	 * "http://developer.android.com/reference/android/telephony/SmsMessage.html#createFromPdu(byte[])"
	 * >source</a> }
	 * 
	 * @param intent
	 */
	public YSMSEvent(Intent intent) {
		Bundle data = intent.getExtras();
		// YLog.d("SMS", data.toString());
		Object messages[] = (Object[]) data.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int n = 0; n < messages.length; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
			YLog.d("SMS", n + " | " + smsMessage[0].getMessageBody());
		}
		mSender = smsMessage[0].getOriginatingAddress();
		mMessage = smsMessage[0].getMessageBody();
	}

	/**
	 * @return sender of SMS
	 */
	public String getSender() {
		return mSender;
	}

	/**
	 * @return SMS text
	 */
	public String getMessage() {
		return mMessage;
	}

	@Override
	public long getId() {
		return ID;
	}
}
