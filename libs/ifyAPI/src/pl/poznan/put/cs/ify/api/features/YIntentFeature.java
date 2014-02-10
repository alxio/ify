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

import java.util.ArrayList;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * Feature for sending Intents
 * 
 * @see http://developer.android.com/reference/android/content/Intent.html
 * 
 */
public class YIntentFeature extends YFeature {

	private Context mContext;

	@Override
	public long getId() {
		return Y.Intent;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mContext = srv.getContext();
	}

	@Override
	public void uninitialize() {
		mContext = null;
	}

	public void sendIntentBroadcast(Bundle data, String action,
			ArrayList<String> categories) {
		Intent broadcast = new Intent();
		if (categories != null && !categories.isEmpty()) {
			for (String cat : categories) {
				broadcast.addCategory(cat);
			}
		}
		if (action != null && !action.isEmpty()) {
			broadcast.setAction(action);
		}
		if (data != null && !data.isEmpty()) {
			broadcast.putExtras(data);
		}
		mContext.sendBroadcast(broadcast);
	}
}
