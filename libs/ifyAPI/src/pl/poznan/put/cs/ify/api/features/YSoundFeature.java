package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class YSoundFeature extends YFeature {

	private MediaPlayer mMediaPlayer;

	@Override
	public int getId() {
		return Y.Sound;
	}

	public void playSound(Uri uri) {
		if (mMediaPlayer == null) {
			mMediaPlayer = MediaPlayer.create(mContext, uri);
		}
		if (mMediaPlayer != null) {

			if (!mMediaPlayer.isPlaying()) {
				mMediaPlayer.start();
				mMediaPlayer
						.setOnCompletionListener(new OnCompletionListener() {

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
	protected void init(IYReceiptHost srv) {
		mContext = srv.getContext();
	}

	@Override
	public void uninitialize() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
		}
	}
}
