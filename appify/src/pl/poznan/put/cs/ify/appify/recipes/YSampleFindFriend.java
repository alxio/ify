package pl.poznan.put.cs.ify.appify.recipes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YGPSEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YCommData;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.api.params.YPosition;

/**
 * Sample recipe tracking position of people in group and sending notification
 * to people who are near. Uses GPS, Group and Notification.
 */
public class YSampleFindFriend extends YRecipe {
	private YComm comm;
	private YPosition mLastPos;
	private int mLastTime;
	// set of people who are already known to be near.
	private Set mAlreadyNear = new HashSet();

	@Override
	public long requestFeatures() {
		return Y.GPS | Y.Group | Y.Notification;
	}

	@Override
	public void init() {
		// Creates comm object asking server for new data every 20 seconds
		comm = mFeatures.getGroup().createPoolingComm(this, "developers", 20);
	}

	@Override
	public void requestParams(YParamList params) {
		// name of group connected with recipe
		params.add("Group", YParamType.Group, "test");
		// max distance in meter to send notification
		params.add("Range", YParamType.Integer, 100);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.GPS) {
			YGPSEvent ge = (YGPSEvent) event;
			// save our last position and time
			mLastPos = ge.getPosition();
			mLastTime = (int) (System.currentTimeMillis() / 1000);

			// create structure for data
			HashMap data = new HashMap();

			// creates YParams to put in data
			YParam pos = YParam.createPosition(mLastPos);
			YParam time = new YParam(YParamType.Integer, mLastTime);

			data.put("position", pos);
			data.put("time", time);

			// sends created data to group
			comm.broadcastEvent(1, data);
		}
		if (event.getId() == Y.Group) {
			YGroupEvent ge = (YGroupEvent) event;
			YCommData data = ge.getData();

			// get sender of message
			String user = data.getUserData().getId();

			if (user.equals(comm.getMyId())) {
				return; // Message from myself, ignore that.
			}

			YPosition otherPos = (YPosition) data.getData("position")
					.getValue();
			Integer otherTime = (Integer) data.getData("time").getValue();

			// only do anything if we know our position
			if (mLastPos != null) {
				// calculate distance in meters to other person
				float dist = otherPos.getDistance(mLastPos);
				boolean isNear = dist < mParams.getInteger("Range");

				// user was already near but is no more...
				if (mAlreadyNear.contains(user) && !isNear) {
					//...so remove him
					mAlreadyNear.remove(user);
				}
				// user wasn't near but now he is
				else if (!mAlreadyNear.contains(user) && isNear) {
					int currentTime = (int) (System.currentTimeMillis() / 1000);
					// check if data is actual (2 minutes is OK)
					if (currentTime - mLastTime < 120
							&& currentTime - otherTime < 120) {
						String message = user + " is near (" + dist + "m).";
						// send notification
						mFeatures.getNotification().createNotification(message,
								this);
						// sets user as already near
						mAlreadyNear.add(user);
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return "YFindFriend";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleFindFriend();
	}

}