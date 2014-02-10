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
package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YSMSEvent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;
/**
 * Feature for sending and reading SMS.
 */
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
		ContentValues values = new ContentValues();
		values.put("address", phoneNumber);// sender name
		values.put("body", message);
		mHost.getContext().getContentResolver()
				.insert(Uri.parse("content://sms/sent"), values);
	}
}
