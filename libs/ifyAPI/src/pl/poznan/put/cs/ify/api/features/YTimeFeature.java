package pl.poznan.put.cs.ify.api.features;

import java.sql.Date;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.params.YDate;

public class YTimeFeature extends YFeature{
	
	private BroadcastReceiver mTimeReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Calendar current = Calendar.getInstance();
			YDate d = new YDate(current.getTimeInMillis());
			sendNotification(new YTimeEvent(d));
		}
	};

	@Override
	public int getId() {
		return Y.Time;
	}

	@Override
	protected void init(IYReceiptHost srv) {
		mContext = srv.getContext();
		mContext.registerReceiver(mTimeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mTimeReceiver);
	}

}
