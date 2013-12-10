package pl.poznan.put.cs.ify.api.features;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneStateReceiver extends BroadcastReceiver {

	interface OnPhoneStateChangedListener {
		void onReceive(Intent i);
	}

	private OnPhoneStateChangedListener mListener;

	@Override
	public void onReceive(Context arg0, Intent intent) {
		if (mListener != null) {
			mListener.onReceive(intent);
		}
	}

	public void setListener(OnPhoneStateChangedListener l) {
		mListener = l;
	}
}
