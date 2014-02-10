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
import pl.poznan.put.cs.ify.api.core.ActiveRecipeInfo;
import pl.poznan.put.cs.ify.api.core.YAbstractRecipeService;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/**
 * Feature for handling text messages send from application UI.
 *
 */
public class YTextFeature extends YFeature {

	private BroadcastReceiver mSendTextReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			ActiveRecipeInfo info = intent
					.getParcelableExtra(YAbstractRecipeService.INFO);
			String text = intent.getStringExtra(YAbstractRecipeService.TEXT);
			if (info == null) {
				sendNotification(new YTextEvent(text));
			} else {
				YLog.d("SERVICE", "Text to recipe" + info.getId());
				sendNotification(new YTextEvent(text), info.getId());
			}
		}
	};
	private Context mContext;

	@Override
	public long getId() {
		return Y.Text;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mContext = srv.getContext();

		IntentFilter sendTextFilter = new IntentFilter(
				YAbstractRecipeService.ACTION_SEND_TEXT);
		mContext.registerReceiver(mSendTextReceiver, sendTextFilter);
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mSendTextReceiver);
	}

}
