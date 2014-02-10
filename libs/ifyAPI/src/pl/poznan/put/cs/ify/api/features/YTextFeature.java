package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.core.ActiveRecipeInfo;
import pl.poznan.put.cs.ify.api.core.YAbstractRecipeService;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class YTextFeature extends YFeature {

	private BroadcastReceiver mSendTextReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			ActiveRecipeInfo info = intent
					.getParcelableExtra(YAbstractRecipeService.INFO);
			String text = intent.getStringExtra(YAbstractRecipeService.TEXT);
			if (info == null) {
				sendNotification(new YTextEvent(text));
			} else {
				YLog.d("SERVICE", "Text to recipe" + info.getId());
				sendNotification(new YTextEvent(text), info.getId());
			}
		}
	};
	private Context mContext;

	@Override
	public long getId() {
		return Y.Text;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mContext = srv.getContext();

		IntentFilter sendTextFilter = new IntentFilter(
				YAbstractRecipeService.ACTION_SEND_TEXT);
		mContext.registerReceiver(mSendTextReceiver, sendTextFilter);
	}

	@Override
	public void uninitialize() {
		mContext.unregisterReceiver(mSendTextReceiver);
	}

}
