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
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ServiceHandler extends Handler {

	public static final String LOGIN = "pl.poznan.put.cs.ify.api.core.LOGIN";
	public static final String PASSWD = "pl.poznan.put.cs.ify.api.core.PASSWD";
	public static final String MESSAGE = "pl.poznan.put.cs.ify.api.core.MESSAGE";

	public ServiceHandler(ServiceCommunication comm) {
		mComm = comm;
	}

	public interface ServiceCommunication {
		void onRequestEnableRecipe(Bundle data);

		void onRequestAvailableRecipes();

		void onRequestDisableRecipe(int id);

		void onRequestActiveRecipes();

		void onRequestRestartAllGroupRecipes();

		void onRequestLogin(String username, String password);

		void onRequestLogout();

		void onRequestRemoveAvailableRecipe(String recipeName);
	}

	public static final int REGISTER_CLIENT = 1;
	public static final int REGISTER_Recipe = 2;
	public static final int REQUEST_AVAILABLE_RecipeS = 3;
	public static final int REQUEST_DISABLE_RECIPE = 4;
	public static final int REQUEST_ACTIVE_RECIPES = 5;
	public static final int REQUEST_RESTART_GROUP_RECIPES = 6;
	public static final int REGISTER_CLIENT_REPLY = 7;

	public static final int REQUEST_LOGIN = 8;
	public static final int RESULT_LOGIN = 9;
	public static final int REQUEST_LOGOUT = 10;
	public static final int RESPONSE_AVAILABLE_RECIPES = 11;
	public static final int RESPONSE_ACTIVE_RECIPES = 12;
	public static final int LOGOUT = 13;
	public static final int REQUEST_REMOVE_AVAILABLE_RECIPE = 14;

	public static final int SUCCESS = 1;
	public static final int FAILURE = 2;

	private Messenger mReplyTo;
	private ServiceCommunication mComm;

	public Messenger getActivityMessenger() {
		return mReplyTo;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.d("TEMP", "Message obtained " + msg.what);

		switch (msg.what) {
		case REGISTER_CLIENT:
			mReplyTo = msg.replyTo;
			try {
				Message reply = Message.obtain(null, REGISTER_CLIENT_REPLY);
				mReplyTo.send(reply);
			} catch (RemoteException e) {
			}
			break;
		case REGISTER_Recipe:
			handleRegisterRecipe(msg.getData());
			break;
		case REQUEST_AVAILABLE_RecipeS:
			handleRequestAvailableRecipes();
			break;
		case REQUEST_DISABLE_RECIPE:
			handleDisableRecipe(msg.arg1);
			break;
		case REQUEST_ACTIVE_RECIPES:
			handleRequestActiveRecipes();
			break;
		case REQUEST_RESTART_GROUP_RECIPES:
			handleRestartAllGroupRecipes();
			break;
		case REQUEST_LOGIN:
			handleRequestLogin(msg.getData());
			break;
		case REQUEST_LOGOUT:
			handleRequestLogout();
		case REQUEST_REMOVE_AVAILABLE_RECIPE:
			handleRemoveAvailableRecipe(msg.getData());
		default:
			break;
		}
	}

	private void handleRemoveAvailableRecipe(Bundle msg) {
		mComm.onRequestRemoveAvailableRecipe(msg
				.getString(YAbstractRecipeService.Recipe));
	}

	private void handleRequestLogout() {
		mComm.onRequestLogout();
	}

	private void handleRequestLogin(Bundle msg) {
		String name = msg.getString(LOGIN);
		String password = msg.getString(PASSWD);
		mComm.onRequestLogin(name, password);
	}

	private void handleRestartAllGroupRecipes() {
		mComm.onRequestRestartAllGroupRecipes();
	}

	private void handleRequestActiveRecipes() {
		mComm.onRequestActiveRecipes();
	}

	private void handleDisableRecipe(int id) {
		mComm.onRequestDisableRecipe(id);
	}

	private void handleRequestAvailableRecipes() {
		mComm.onRequestAvailableRecipes();
	}

	private void handleRegisterRecipe(Bundle data) {
		mComm.onRequestEnableRecipe(data);
	}

}
