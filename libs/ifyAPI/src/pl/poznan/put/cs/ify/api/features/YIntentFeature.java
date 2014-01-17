package pl.poznan.put.cs.ify.api.features;

import java.util.ArrayList;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class YIntentFeature extends YFeature {

	private Context mContext;

	@Override
	public long getId() {
		return Y.Intent;
	}

	@Override
	protected void init(IYReceiptHost srv) {
		mContext = srv.getContext();
	}

	@Override
	public void uninitialize() {
		mContext = null;
	}

	public void sendIntentBroadcast(Bundle data, String action,
			ArrayList<String> categories) {
		Intent broadcast = new Intent();
		if (categories != null && !categories.isEmpty()) {
			for (String cat : categories) {
				broadcast.addCategory(cat);
			}
		}
		if (action != null && !action.isEmpty()) {
			broadcast.setAction(action);
		}
		if (data != null && !data.isEmpty()) {
			broadcast.putExtras(data);
		}
		mContext.sendBroadcast(broadcast);
	}
}
