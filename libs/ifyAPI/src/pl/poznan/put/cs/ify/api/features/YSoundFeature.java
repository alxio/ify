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
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class YSoundFeature extends YFeature {

	private MediaPlayer mMediaPlayer;

	@Override
	public long getId() {
		return Y.Sound;
	}

	/**
	 * Play specified sound
	 * 
	 * @param uri
	 *            file with sound
	 */
	public void playSound(Uri uri) {
		if (mMediaPlayer == null) {
			mMediaPlayer = MediaPlayer.create(mHost.getContext(), uri);
		}
		if (mMediaPlayer != null) {

			if (!mMediaPlayer.isPlaying()) {
				mMediaPlayer.start();
				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						mMediaPlayer.release();
						mMediaPlayer = null;
					}
				});
			}
		}
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mHost = srv;
	}

	@Override
	public void uninitialize() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
		}
	}
}
