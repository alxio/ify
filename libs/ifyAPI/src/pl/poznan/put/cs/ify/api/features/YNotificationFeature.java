package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class YNotificationFeature extends YFeature {

	private NotificationManager mNotificationManager;
	private int mIcon;

	@Override
	public long getId() {
		return Y.Notification;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mIcon = srv.getNotificationIconId();
		mNotificationManager = (NotificationManager) mHost.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void uninitialize() {
	}

	/**
	 * Displays notification with given text connected with given recipe.
	 * 
	 * @param text
	 * @param recipe
	 */
	public void createNotification(String text, YRecipe recipe) {
		// Sets an ID for the notification, so it can be updated
		int notifyID = recipe.getId();
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Notification n = new NotificationCompat.Builder(mHost.getContext()).setSmallIcon(mIcon).setContentText(text)
				.setContentTitle(recipe.getName()).setSound(alarmSound).build();
		mNotificationManager.notify(notifyID, n);
	}
}
