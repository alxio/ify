/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
