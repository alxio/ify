package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YGPSEvent;
import pl.poznan.put.cs.ify.api.features.YNotificationFeature;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YCommData;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.api.params.YPosition;

public class YFindFriend extends YReceipt {
	private YComm comm;
	private YPosition mLastPos;

	@Override
	public long requestFeatures() {
		return Y.GPS | Y.Group | Y.Notification;
	}

	@Override
	public void init() {
		YGroupFeature gf = (YGroupFeature) mFeatures.get(Y.Group);
		comm = gf.createPoolingComm(this, "developers", 60);
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("Group", YParamType.Group, "developers");
		params.add("Range", YParamType.Integer, 100);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.GPS) {
			YGPSEvent ge = (YGPSEvent) event;
			YPosition pos = ge.getPosition();
			mLastPos = pos;
			Log.d("SENDING POS:" + pos);
			comm.broadcastEvent(1, "position", YParam.createPosition(pos));
		}
		if (event.getId() == Y.Group) {
			YGroupEvent ge = (YGroupEvent) event;
			YCommData data = ge.getData();
			if (ge.getData().getUserData().getId().equals(comm.getMyId())) {
				return; // Message from myself, ignore that.
			}
			YPosition other = (YPosition) data.getData("position").getValue();
			Log.d("GOT MSG:" + data.toJson().toString());
			if (mLastPos != null) {
				float dist = other.getDistance(mLastPos);
				if (dist < mParams.getInteger("Range")) {
					((YNotificationFeature) mFeatures.get(Y.Notification)).createNotification(ge.getData()
							.getUserData().getId()
							+ " is near (" + dist + "m).", this);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "YFindFriend";
	}

	@Override
	public YReceipt newInstance() {
		return new YFindFriend();
	}

}
