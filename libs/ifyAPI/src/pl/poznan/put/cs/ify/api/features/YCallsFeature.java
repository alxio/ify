package pl.poznan.put.cs.ify.api.features;

import java.lang.reflect.Method;
import java.util.Set;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.features.PhoneStateReceiver.OnPhoneStateChangedListener;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.ITelephony;

public class YCallsFeature extends YFeature {

	private TelephonyManager mTelephonyManager;
	private PhoneStateReceiver mPhoneStateReceiver;
	private Context mContext;
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
	protected void init(IYReceiptHost srv) {
		mContext = srv.getContext();
		mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		mPhoneStateReceiver = new PhoneStateReceiver();
		mPhoneStateReceiver.setListener(mListener);
		IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		srv.getContext().registerReceiver(mPhoneStateReceiver, intentFilter);
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mPhoneStateReceiver);
	}

	/**
	 * TODO
	 */
	public void discardCurrentCall() {
		try {

			// TODO: Try calling it once, while initializing for optimalization.
			Class c = Class.forName(mTelephonyManager.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			ITelephony telephonyService = (ITelephony) m.invoke(mTelephonyManager);

			telephonyService.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
