package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class YRawPlayerFeature extends YFeature {
	private final int SAMPLE_RATE = 16000;
	private AudioTrack mTrack;

	/**
	 * Plays sound of given frequency for given period of time
	 * 
	 * @param freq
	 *            frequency in Hz
	 * @param duration
	 *            in seconds
	 */
	public void playFreq(int freq, int duration) {
		play(genTone(freq, duration), SAMPLE_RATE);
	}

	private short[] genTone(int freq, int duration) {
		short[] buffer = new short[SAMPLE_RATE * duration];
		float angle = 0;
		for (int i = 0; i < buffer.length; i++) {
			float angular_frequency = (float) (2 * Math.PI) * freq / SAMPLE_RATE;
			buffer[i] = (short) (Short.MAX_VALUE * ((float) Math.sin(angle)));
			angle += angular_frequency;
		}
		return buffer;
	}

	/**
	 * Play sounds array with given sample rate
	 * 
	 * @param sounds
	 *            array of sound values
	 * @param rate
	 *            sample rate in Hz
	 */
	public void play(short[] sounds, int rate) {
		try {
			mTrack = new AudioTrack(AudioManager.STREAM_MUSIC, rate, AudioFormat.CHANNEL_OUT_MONO,
					AudioFormat.ENCODING_PCM_16BIT, sounds.length * 2, AudioTrack.MODE_STATIC);
			mTrack.write(sounds, 0, sounds.length);
			mTrack.play();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public static final long ID = Y.RawPlayer;

	@Override
	public long getId() {
		return ID;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uninitialize() {
		// TODO Auto-generated method stub

	}
}
