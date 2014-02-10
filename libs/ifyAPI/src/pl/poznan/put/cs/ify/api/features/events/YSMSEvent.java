/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
/**
 * Represents incoming SMS.
 *
 */
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
