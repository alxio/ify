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

import java.util.Calendar;
import java.util.Date;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.events.YTimeEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/**
 * Sends {@link #YTimeEvent} every minute, providing current system time.
 *
 */
public class YTimeFeature extends YFeature{
	
	private BroadcastReceiver mTimeReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Calendar current = Calendar.getInstance();
			Date d = new Date(current.getTimeInMillis());
			sendNotification(new YTimeEvent(d));
		}
	};

	@Override
	public long getId() {
		return Y.Time;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mHost = srv;
		mHost.getContext().registerReceiver(mTimeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
	}

	@Override
	public void uninitialize() {
		mHost.getContext().unregisterReceiver(mTimeReceiver);
	}

}
