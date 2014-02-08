package pl.poznan.put.cs.ify.api.core;

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

		void onLoginFailure(String string);

		void onLoginSuccessful();

		void onLogout();
	}

	private ActivityCommunication mComm;

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.d("TEMP", "Message delivered do Activity " + msg.what);
		switch (msg.what) {
		case ServiceHandler.RESPONSE_AVAILABLE_RECIPES:
			mComm.onAvailableRecipesListReceived(msg.getData());
			break;
		case ServiceHandler.RESPONSE_ACTIVE_RECIPES:
			mComm.onActiveRecipesListReceiverd(msg.getData());
			break;
		case ServiceHandler.RESULT_LOGIN:
			if (msg.arg1 == ServiceHandler.SUCCESS) {
				mComm.onLoginSuccessful();
			} else if (msg.arg1 == ServiceHandler.FAILURE) {
				mComm.onLoginFailure(msg.getData().getString(
						ServiceHandler.MESSAGE));
			}
			break;
		case ServiceHandler.LOGOUT:
			mComm.onLogout();
			break;
		default:
			break;
		}
	}
}
