package pl.poznan.put.cs.ify.api.core;

import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class YLogsManager implements ILog {

	private BroadcastReceiver mToggleLogReceiver;
	private BroadcastReceiver mGetLogsReceiver;
	private Context mContext;
	private YLog mLog;

	public YLogsManager(Context context) {
		mContext = context;
		mLog = new YLog(context);
	}

	public void initBroadcastReceivers() {
		IntentFilter f = new IntentFilter(YAbstractRecipeService.TOGGLE_LOG);
		mToggleLogReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				YLog.toggleView();
			}
		};
		mContext.registerReceiver(mToggleLogReceiver, f);

		mGetLogsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String tag = intent
						.getStringExtra(YAbstractRecipeService.Recipe_TAG);
				sendArchivedLogs(tag);
			}
		};
		IntentFilter getLogsFilter = new IntentFilter(
				YAbstractRecipeService.REQUEST_LOGS);
		mContext.registerReceiver(mGetLogsReceiver, getLogsFilter);
	}

	public void unregisterReceivers() {
		if (mGetLogsReceiver != null) {
			mContext.unregisterReceiver(mGetLogsReceiver);
		}
		if (mToggleLogReceiver != null) {
			mContext.unregisterReceiver(mToggleLogReceiver);
		}
	}

	@Override
	public void sendArchivedLogs(String tag) {
		if (tag != null) {
			Log.d("SendLogs", "" + tag);
			Intent i = new Intent(
					YAbstractRecipeService.ACTION_Recipe_LOGS_RESPONSE);
			i.putExtra(YAbstractRecipeService.Recipe_TAG, tag);
			i.putExtra(YAbstractRecipeService.Recipe_LOGS,
					YLog.getFilteredHistory(tag));
			mContext.sendBroadcast(i);
		}

	}
}
