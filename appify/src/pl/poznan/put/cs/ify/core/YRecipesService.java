package pl.poznan.put.cs.ify.core;

import java.util.List;
import java.util.Map.Entry;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.IActiveRecipesProvider;
import pl.poznan.put.cs.ify.api.core.IAvailableRecipesManager;
import pl.poznan.put.cs.ify.api.core.ILog;
import pl.poznan.put.cs.ify.api.core.ISecurity;
import pl.poznan.put.cs.ify.api.core.YAbstractRecipeService;
import pl.poznan.put.cs.ify.api.core.YLogsManager;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.security.YSecurity;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.app.persistance.RecipeFromDatabase;
import pl.poznan.put.cs.ify.app.persistance.RecipesDatabaseHelper;
import pl.poznan.put.cs.ify.appify.R;

public class YRecipesService extends YAbstractRecipeService {

	private int NOTIFICATION = R.string.app_name;
	private NotificationManager mNotificationManager;

	@Override
	public void onCreate() {
		super.onCreate();
		RecipesDatabaseHelper dbHelper = new RecipesDatabaseHelper(this);
		mRecipeID = dbHelper.getMaxId();
		List<RecipeFromDatabase> activatedRecipes = dbHelper
				.getActivatedRecipes();
		for (RecipeFromDatabase recipeFromDatabase : activatedRecipes) {
			int reviveRecipe = reviveRecipe(recipeFromDatabase);
			if (reviveRecipe == -1) {
				dbHelper.removeRecipe(recipeFromDatabase.id);
			}
		}
		showNotification();
	}

	@Override
	public void disableRecipe(Integer id) {
		YRecipe recipe = mActiveRecipesManager.get(id);
		super.disableRecipe(id);
		RecipesDatabaseHelper recipesHelper = new RecipesDatabaseHelper(this);
		recipesHelper.removeRecipe(id);
		YLog.d("SERVICE", "DeactivateRecipe: " + recipe.getName() + " ,ID: "
				+ id);
		showNotification();
	}

	@Override
	public int enableRecipe(String name, YParamList params) {
		int id = super.enableRecipe(name, params);
		showNotification();
		RecipesDatabaseHelper recipeshelper = new RecipesDatabaseHelper(this);
		YRecipe recipe = mActiveRecipesManager.get(id);
		recipeshelper.saveRecipe(recipe, id);
		return id;
	}

	@SuppressWarnings("deprecation")
	private void showNotification() {
		int active = mActiveRecipesManager.getMap().size();
		int icon;
		switch (active) {
		case 0:
			icon = R.drawable.ify;
			break;
		case 1:
			icon = R.drawable.y1;
			break;
		case 2:
			icon = R.drawable.y2;
			break;
		case 3:
			icon = R.drawable.y3;
			break;
		case 4:
			icon = R.drawable.y4;
			break;
		case 5:
			icon = R.drawable.y5;
			break;
		case 6:
			icon = R.drawable.y6;
			break;
		case 7:
			icon = R.drawable.y7;
			break;
		case 8:
			icon = R.drawable.y8;
			break;
		case 9:
			icon = R.drawable.y9;
			break;
		default:
			icon = R.drawable.y10;
			break;
		}
		CharSequence text = getText(NOTIFICATION);

		Notification notification = new Notification(icon, text,
				System.currentTimeMillis());
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("POSITION", 1);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);

		notification.setLatestEventInfo(this, text,
				"Active recipes: " + active, contentIntent);

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFICATION, notification);
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
		return new AvailableRecipesManager(this);
	}

	@Override
	protected ILog getLogManager() {
		YLogsManager yLogsManager = new YLogsManager(this);
		yLogsManager.initBroadcastReceivers();
		return yLogsManager;
	}

	private int reviveRecipe(RecipeFromDatabase recipeFromDatabase) {
		YRecipe r = mAvailableRecipesManager.getRecipe(recipeFromDatabase.name);
		if (r == null) {
			return -1;
		}
		YRecipe recipe = r.newInstance();
		long feats = recipe.requestFeatures();
		YFeatureList features = new YFeatureList(feats);
		initFeatures(features);
		recipeFromDatabase.yParams.setFeatures(feats);
		recipe.initialize(this, recipeFromDatabase.yParams, features,
				recipeFromDatabase.id, recipeFromDatabase.timestamp);
		for (Entry<Long, YFeature> entry : features) {
			YLog.d("SERVICE", "RegisterRecipe: " + recipe.getName() + " to "
					+ entry.getKey());
			entry.getValue().registerRecipe(recipe);
		}
		mActiveRecipesManager.put(recipeFromDatabase.id, recipe);
		return recipeFromDatabase.id;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mNotificationManager != null) {
			mNotificationManager.cancel(NOTIFICATION);
		}
		((YLogsManager) mLog).unregisterReceivers();
	}

	@Override
	public int getNotificationIconId() {
		return R.drawable.ify_n;
	}
}
