package pl.poznan.put.cs.ify.android.receivers;

import java.util.ArrayList;

import android.content.BroadcastReceiver;

public abstract class YBroadcastReceiver extends BroadcastReceiver {
	
	protected ArrayList<IReceiver> notifiables = new ArrayList<IReceiver>();
	
	public void registerReceiver(IReceiver receiver) {
		notifiables.add(receiver);
	}
	
	public void unregisterReceiver(IReceiver receiver) {
		notifiables.remove(receiver);
	}
}
