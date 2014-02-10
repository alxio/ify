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
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Feature for system volume management.
 */
public class YAudioManagerFeature extends YFeature {

	private AudioManager mAudioManager;

	@Override
	public long getId() {
		return Y.AudioManager;
	}

	/**
	 * Sets the ringer mode to silent mode - it will mute the volume and will
	 * not vibrate.
	 */
	public void setSilent() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		mHost.getContext().sendBroadcast(
				new Intent(AudioManager.RINGER_MODE_CHANGED_ACTION));
	}

	/**
	 * Sets the ringer mode to silent mode - it will mute the volume and
	 * vibrate.
	 */
	public void setNormal() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		mHost.getContext().sendBroadcast(
				new Intent(AudioManager.RINGER_MODE_CHANGED_ACTION));
	}

	/**
	 * Sets the ringer mode to silent mode - it will be audible and may vibrate
	 * according to user settings.
	 */
	public void setVibrate() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		mHost.getContext().sendBroadcast(
				new Intent(AudioManager.RINGER_MODE_CHANGED_ACTION));
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mAudioManager = (AudioManager) srv.getContext().getSystemService(
				Context.AUDIO_SERVICE);
		mHost = srv;
	}

	@Override
	public void uninitialize() {

	}

}
