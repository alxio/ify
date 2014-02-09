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
import pl.poznan.put.cs.ify.api.features.events.YBatteryEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class YBatteryFeature extends YFeature {
	public static final long ID = Y.Battery;
	public static final String NAME = "YBatteryFeature";

	@Override
	public long getId() {
		return ID;
	}

	private int mLevel = -1;
	private BroadcastReceiver mBatInfoReceiver;

	/**
	 * @return last received battery level percentage.
	 */
	public int getLastLevel() {
		return mLevel;
	}

	@Override
	public void uninitialize() {
		mHost.getContext().unregisterReceiver(mBatInfoReceiver);
	}

	@Override
	public void init(IYRecipeHost srv) {
		mBatInfoReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent intent) {
				mLevel = intent.getIntExtra("level", 0);
				sendNotification(new YBatteryEvent(mLevel));
			}
		};
		mHost.getContext().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

}
