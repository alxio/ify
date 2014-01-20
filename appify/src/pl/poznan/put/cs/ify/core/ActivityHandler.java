package pl.poznan.put.cs.ify.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ActivityHandler extends Handler {

	public ActivityHandler(ActivityCommunication comm) {
		mComm = comm;
	}

	public interface ActivityCommunication {
		void onAvailableRecipesListReceived(Bundle data);

		void onActiveRecipesListReceiverd(Bundle data);
	}

	public static final int REGISTERED = 1;
	public static final int AVAILABLE_RecipeS_RESPONSE = 2;
	public static final int ACTIVE_RECIPES_RESPONSE = 3;
	private ActivityCommunication mComm;

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.d("TEMP", "Message delivered do Activity " + msg.what);
		switch (msg.what) {
		case AVAILABLE_RecipeS_RESPONSE:
			mComm.onAvailableRecipesListReceived(msg.getData());
			break;
		case ACTIVE_RECIPES_RESPONSE:
			mComm.onActiveRecipesListReceiverd(msg.getData());
			break;
		default:
			break;
		}
	}
}
