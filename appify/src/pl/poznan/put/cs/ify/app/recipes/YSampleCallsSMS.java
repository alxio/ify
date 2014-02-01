package pl.poznan.put.cs.ify.app.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YCallsEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;


/**
 * Simple recipe, that discards all incoming calls and sends SMS to caller.
 */
public class YSampleCallsSMS extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Calls;
	}

	@Override
	public void requestParams(YParamList params) {
		//Message to send in SMS
		params.add("MSG",YParamType.String, "Sorry, I'm busy.");
	}

	@Override
	public void handleEvent(YEvent event) {
		//event is incoming call
		if(event.getId() == Y.Calls){
			YCallsEvent e = (YCallsEvent) event;
			//extract phone number
			String phone = e.getIncomingNumber();
			//discard call
			mFeatures.getCalls().discardCurrentCall();
			//send sms
			mFeatures.getSMS().sendSMS(phone, mParams.getString("MSG"));
		}
	}

	@Override
	public String getName() {
		return "YSampleCallsSMS";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleCallsSMS();
	}
}