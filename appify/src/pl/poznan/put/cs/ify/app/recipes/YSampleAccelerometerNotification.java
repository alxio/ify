package pl.poznan.put.cs.ify.app.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YNotificationFeature;
import pl.poznan.put.cs.ify.api.features.events.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Sample recipe reading data from Accelerometer and showing notification if it's low enough.
 */
public class YSampleAccelerometerNotification extends YRecipe {
	
	//flag preventing from showing multiple notifications
	private boolean alreadyFallen = false;

	@Override
	public long requestFeatures() {
		return Y.Notification | Y.Accelerometer;
	}

	@Override
	public void requestParams(YParamList params) {
		//Integer param with value of squared acceleration which triggers recipe
		params.add("MIN", YParamType.Integer, 10);
	}

	@Override
	public void init() {
	}

	@Override
	public void handleEvent(YEvent event) {
		//Ignore events not coming from Accelerometer
		if (event.getId() != Y.Accelerometer)
			return;
		//Cast event to gain access to details.
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		//Get squared length of acceleration vector
		float grall = e.getVector().getLengthSquere();
		//Displays squared length of acceleration vector in Logs
		Log.d(grall + "");
		//Get param value
		int min = mParams.getInteger("MIN");
		//It acceleration is small enough and we didn't already show notification
		if (grall < min && !alreadyFallen) {
			//Set flag 
			alreadyFallen = true;
			//Gets the feature that was requested before
			YNotificationFeature feat = mFeatures.getNotification();
			feat.createNotification( "Oops, my phone has falled.", this);
		}
	}

	@Override
	public String getName() {
		return "YSampleAccelerometerNotification";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleAccelerometerNotification();
	}
}