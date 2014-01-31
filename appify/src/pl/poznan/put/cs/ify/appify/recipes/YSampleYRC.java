package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 *	Simple IRC - like chat, using YTextEvent, YGroupFeature and YLog.
 */
public class YSampleYRC extends YRecipe {
 //Communication object
	private YComm comm;
	//Tag for our group events - all group events in this recipe are same, so we only need one tag
	int EVENT_TAG = 1;

	@Override
	public void requestParams(YParamList params) {
	 //String param named "Group" with default value "test"
		params.add("Group", YParamType.String, "test");
	}

	@Override
	public long requestFeatures() {
	 //Recipe usues Group and Text features
		return Y.Group | Y.Text;
	}

	@Override
	public void init() {
	 //Takes YGroupFeature from requested features
		YGroupFeature gf = mFeatures.getGroup();
		//Extracts name of group from params
		String groupName = mParams.getString("Group");
		//Creates Comm object connected with given group name which looks for new data every 5 seconds
		comm = gf.createPoolingComm(this, groupName, 5);
	}

	@Override
	public void handleEvent(YEvent event) {
	 //handle text event
		if (event.getId() == Y.Text) {
		 //cast event to right type
			YTextEvent te = (YTextEvent) event;
			//broadcasts event with one data field with key "text" and String value taken from text event 
			comm.broadcastEvent(EVENT_TAG, "text", new YParam(YParamType.String, te.getText()));
			//manually asks server for events
			comm.pool();
		}
		//handle group event
		if (event.getId() == Y.Group) {
		 //cast event to right type
			YGroupEvent ge = (YGroupEvent) event;
			//extracts custom data field called "text"
			String message = ge.getData().getDataAsString("text");
			//extracts sender login (ID) from auto-generated UserData
			String sender = ge.getData().getUserData().getId();
			//prints sender name and message to logs
			Log.i("" + "<" + sender + "> " + message);
			//manually asks server for next events
			comm.pool();
		}
	}

	@Override
	public String getName() {
		return "SampleYRC";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleYRC();
	}
}