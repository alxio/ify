package pl.poznan.put.cs.ify.features;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class YBattery extends YTrigger {
	int mLevel = -1;
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			mLevel = intent.getIntExtra("level", 0);
			tryActivate();
		}
	};
	@Override
	public void initialize() {
		mContext.registerReceiver(this.mBatInfoReceiver, 
	    	    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}	
	@Override
	public String getName() {
		return "YBattery";
	}
	public int getLevel(){
		return mLevel;
	}
}
