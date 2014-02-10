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
import pl.poznan.put.cs.ify.api.ShortcutActivity;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YShortcutEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Pair;

/**
 * Feature for creating shortcuts.
 */
public class YShortcutFeature extends YFeature {

	public static final String SHORTCUT_ACTION = "pl.poznan.put.cs.ify.api.features.yshortcutfeature";

	private Context mContext;
	private ArrayList<Pair<Intent, String>> mAddedShortcuts = new ArrayList<Pair<Intent, String>>();

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int id = intent.getIntExtra("KEY", -1);
			if (id != -1) {
				for (YRecipe r : mListeners) {
					if (r.getId() == id) {
						r.tryHandleEvent(new YShortcutEvent(id));
					}
				}
			}
		}
	};

	@Override
	public long getId() {
		return Y.Shortcut;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mContext = srv.getContext();
		IntentFilter f = new IntentFilter(SHORTCUT_ACTION);
		mContext.registerReceiver(mReceiver, f);
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mReceiver);
		for (Pair<Intent, String> p : mAddedShortcuts) {
			Intent addIntent = new Intent();
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, p.first);
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, p.second);
			addIntent
					.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
			mContext.sendBroadcast(addIntent);
		}
		mAddedShortcuts.clear();
	}

	public void createShortcut(YRecipe recipe, String name) {
		Intent shortcutIntent = new Intent(mContext, ShortcutActivity.class);
		shortcutIntent.putExtra("KEY", recipe.getId());
		shortcutIntent.setAction(Intent.ACTION_MAIN);

		Pair<Intent, String> shortcutInfo = new Pair<Intent, String>(
				shortcutIntent, name);
		mAddedShortcuts.add(shortcutInfo);
		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		mContext.sendBroadcast(addIntent);

	}
}
