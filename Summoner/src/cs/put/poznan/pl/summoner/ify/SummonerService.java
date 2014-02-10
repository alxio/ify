package cs.put.poznan.pl.summoner.ify;

import pl.poznan.put.cs.ify.api.core.IActiveRecipesProvider;
import pl.poznan.put.cs.ify.api.core.IAvailableRecipesManager;
import pl.poznan.put.cs.ify.api.core.ILog;
import pl.poznan.put.cs.ify.api.core.ISecurity;
import pl.poznan.put.cs.ify.api.core.YAbstractRecipeService;
import pl.poznan.put.cs.ify.api.security.YSecurity;
import pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback;
import android.content.Intent;
import android.os.Bundle;
import cs.put.poznan.pl.summoner.R;

public class SummonerService extends YAbstractRecipeService {

	public static final String DATA = "cs.put.poznan.pl.summoner.data";
	public static final String ACTION_LOGIN = "cs.put.poznan.pl.summoner.login";
	public static final String ACTION_LOGIN_RESULT = "cs.put.poznan.pl.summoner.login.result";
	public static final String EXTRA_PASSWORD = "cs.put.poznan.pl.summoner.login";
	public static final String EXTRA_USERNAME = "cs.put.poznan.pl.summoner.login";
	public static final String EXTRA_RESULT = "cs.put.poznan.pl.summoner.result";

	private ILoginCallback mLoginCallback = new ILoginCallback() {

		@Override
		public void onLogout() {
		}

		@Override
		public void onLoginSuccess(String username) {
			Intent i = new Intent(ACTION_LOGIN_RESULT);
			i.putExtra(EXTRA_RESULT, true);
			sendBroadcast(i);
		}

		@Override
		public void onLoginFail(String message) {
			Intent i = new Intent(ACTION_LOGIN_RESULT);
			i.putExtra(EXTRA_RESULT, false);
			sendBroadcast(i);
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected ISecurity getSecurityManager() {
		return new YSecurity(this);
	}

	@Override
	protected IActiveRecipesProvider getActiveRecipesManager() {
		return new ActiveRecipesManager();
	}

	@Override
	protected IAvailableRecipesManager getAvailableRecipesManager() {
		return new AvailableRecipesManager();
	}

	@Override
	protected ILog getLogManager() {
		return null;
	}

	@Override
	public int getNotificationIconId() {
		return R.drawable.ic_launcher;
	}
	
	@Override
	public void onRequestDisableRecipe(int id) {
		pauseAll();
	}

	@Override
	public void onRequestEnableRecipe(Bundle data) {
		if (mActiveRecipesManager.getMap().size() == 0) {
			super.onRequestEnableRecipe(data);
		}
	}
}
