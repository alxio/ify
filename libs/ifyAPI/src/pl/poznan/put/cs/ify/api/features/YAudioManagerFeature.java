package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class YAudioManagerFeature extends YFeature {

	private AudioManager mAudioManager;

	@Override
	public long getId() {
		return Y.AudioManager;
	}

	public void setSilent() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		mContext.sendBroadcast(new Intent(
				AudioManager.RINGER_MODE_CHANGED_ACTION));
	}

	public void setNormal() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		mContext.sendBroadcast(new Intent(
				AudioManager.RINGER_MODE_CHANGED_ACTION));
	}

	public void setVibrate() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		mContext.sendBroadcast(new Intent(
				AudioManager.RINGER_MODE_CHANGED_ACTION));
	}

	@Override
	protected void init(IYReceiptHost srv) {
		mAudioManager = (AudioManager) srv.getContext().getSystemService(
				Context.AUDIO_SERVICE);
		mContext = srv.getContext();
	}

	@Override
	public void uninitialize() {

	}

}
