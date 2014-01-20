package pl.poznan.put.cs.ify.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ServiceHandler extends Handler {

	public ServiceHandler(ServiceCommunication comm) {
		mComm = comm;
	}

	public interface ServiceCommunication {
		void onRegisterRecipeRequest(Bundle data);

		void onRequestAvailableRecipes();

		void onDisableRecipeRequest(int id);

		void onRequestActiveRecipes();
	}

	public static final int REGISTER_CLIENT = 1;
	public static final int REGISTER_Recipe = 2;
	public static final int REQUEST_AVAILABLE_RecipeS = 3;
	public static final int REQUEST_DISABLE_RECIPE = 4;
	public static final int REQUEST_ACTIVE_RECIPES = 5;

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
				Message reply = Message.obtain(null, ActivityHandler.REGISTERED);
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
		default:
			break;
		}
	}

	private void handleRequestActiveRecipes() {
		mComm.onRequestActiveRecipes();
	}

	private void handleDisableRecipe(int id) {
		mComm.onDisableRecipeRequest(id);
	}

	private void handleRequestAvailableRecipes() {
		mComm.onRequestAvailableRecipes();
	}

	private void handleRegisterRecipe(Bundle data) {
		mComm.onRegisterRecipeRequest(data);
	}

}
