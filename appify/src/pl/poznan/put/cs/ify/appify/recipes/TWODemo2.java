package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YNotificationFeature;
import pl.poznan.put.cs.ify.api.features.events.YSMSEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class TWODemo2 extends YRecipe {
	private YComm comm;

	@Override
	public void init() {
		YGroupFeature gf = (YGroupFeature) mFeatures.get(Y.Group);
		comm = gf.createPoolingComm(this, mParams.getString("Group"), 5);
	}

	@Override
	public long requestFeatures() {
		return Y.Group | Y.SMS | Y.Notification;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("From", new YParam(YParamType.Number, new String()));
		params.add("Group", new YParam(YParamType.String, "test"));
	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		if (event.getId() == Y.SMS) {
			YSMSEvent smsEvent = (YSMSEvent) event;
String message = smsEvent.getMessage();
			if (smsEvent.getSender().equals(mParams.getNumber("From"))) {
				comm.sendEvent("BROADCAST", 1, "text", new YParam(YParamType.String, message));
			}
		} else if (event.getId() == Y.Group) {
			YGroupEvent groupEvent = (YGroupEvent) event;
			String message = groupEvent.getData().getDataAsString("text");
			YNotificationFeature notifFeature = (YNotificationFeature) mFeatures.get(Y.Notification);
			notifFeature.createNotification(message, this);
		}
	}

	@Override
	public String getName() {
		return "TWODemo2";
	}

	@Override
	public YRecipe newInstance() {
		return new TWODemo2();
	}
}
