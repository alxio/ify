package pl.poznan.put.cs.ify.api.features;

import java.lang.reflect.Method;
import java.util.Set;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.PhoneStateReceiver.OnPhoneStateChangedListener;
import pl.poznan.put.cs.ify.api.features.events.YCallsEvent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

public class YCallsFeature extends YFeature {

	private TelephonyManager mTelephonyManager;
	private PhoneStateReceiver mPhoneStateReceiver;
	private Context mContext;
	private Method mSilenceRinger;
	private Method mEndCall;
	private Object mTelephonyService;
	
	private OnPhoneStateChangedListener mListener = new OnPhoneStateChangedListener() {

		@Override
		public void onReceive(Intent i) {
			Set<String> keySet = i.getExtras().keySet();
			for (String s : keySet) {
				Log.d("TELEPHONY", i.getExtras().get(s) + " nazwa dla Olka " + s
						+ i.getExtras().get(s).getClass().getName());
			}
			YCallsEvent event = new YCallsEvent(i.getExtras().getString("state"), i.getExtras().getString(
					"incoming_number"));
			sendNotification(event);
		}
	};

	@Override
	public long getId() {
		return Y.Calls;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mContext = srv.getContext();
		mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		mPhoneStateReceiver = new PhoneStateReceiver();
		mPhoneStateReceiver.setListener(mListener);
		IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		srv.getContext().registerReceiver(mPhoneStateReceiver, intentFilter);
		try {
			Class c = Class.forName(mTelephonyManager.getClass().getName());
			Method getITelephony = c.getDeclaredMethod("getITelephony");
			getITelephony.setAccessible(true);
			mTelephonyService = getITelephony.invoke(mTelephonyManager);
			mEndCall = mTelephonyService.getClass().getDeclaredMethod("endCall");
			mSilenceRinger = mTelephonyService.getClass().getDeclaredMethod("silenceRinger");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mPhoneStateReceiver);
	}

	public void discardCurrentCall() {
		try {
			mEndCall.invoke(mTelephonyService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void silenceRinger() {
		try {
			mSilenceRinger.invoke(mTelephonyService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
