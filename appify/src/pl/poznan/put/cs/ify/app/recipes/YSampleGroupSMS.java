package pl.poznan.put.cs.ify.app.recipes;

import java.util.HashMap;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YSMSEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Sample recipe forwarding all received SMS to group and showing as notifications and saves to logs.
 */
public class YSampleGroupSMS extends YRecipe {
	private YComm comm;

	@Override
	public void init() {
		//create Comm object connected with group from param polling server every 5 seconds.
		comm = mFeatures.getGroup().createPoolingComm(this, mParams.getString("GROUP"), 5);
	}

	@Override
	public long requestFeatures() {
		return Y.Group | Y.SMS | Y.Notification;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("GROUP", new YParam(YParamType.String, "test"));
	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		//handle SMS
		if (event.getId() == Y.SMS) {
			YSMSEvent smsEvent = (YSMSEvent) event;
			String message = smsEvent.getMessage();
			String sender = smsEvent.getSender();
			
			//create map with values to send
			HashMap<String,YParam> data = new HashMap<String,YParam>();
			
			//put SMS message and sender to group message data
			data.put("message", new YParam(YParamType.String, message));
			data.put("sender", new YParam(YParamType.String, sender));
			
			//send event with tag 1 and data to all people in group
			comm.broadcastEvent(1, data);
			
		//handle group message
		} else if (event.getId() == Y.Group) {
			YGroupEvent groupEvent = (YGroupEvent) event;
			
			//extract SMS message and sender from group message
			String message = groupEvent.getData().getDataAsString("message");
			String sender = groupEvent.getData().getDataAsString("sender");
			
			//create notification with SMS text
			mFeatures.getNotification().createNotification(message, this);
			
			//save SMS text and sender to logs
			Log.i("<"+sender+"> "+message);
		}
	}

	@Override
	public String getName() {
		return "YGroupSMSSample";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleGroupSMS();
	}
}