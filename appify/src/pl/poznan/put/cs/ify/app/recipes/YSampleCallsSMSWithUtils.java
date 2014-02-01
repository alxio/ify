package pl.poznan.put.cs.ify.app.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YCallsEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.api.utils.YPhoneNumberUtils;

/**
 * Simple recipe, that discards incoming calls from given number and sends SMS
 * to caller.
 */
public class YSampleCallsSMSWithUtils extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Calls | Y.SMS;
	}

	@Override
	public void requestParams(YParamList params) {
		// Message to send in SMS
		params.add("MSG", YParamType.String, "Sorry, I'm busy.");
		// Ignored caller's number
		params.add("FROM", YParamType.Number, "606932226");
	}

	@Override
	public void handleEvent(YEvent event) {
		// event is incoming call
		if (event.getId() == Y.Calls) {
			// extract ignored phone number
			String ignoreNumber = getParams().getNumber("FROM");
			YCallsEvent e = (YCallsEvent) event;
			// extract phone number
			String phone = e.getIncomingNumber();
			// discard call
			if (YPhoneNumberUtils.compare(phone, ignoreNumber)) {
				getFeatures().getCalls().discardCurrentCall();
				// send sms
				getFeatures().getSMS().sendSMS(phone, getParams().getString("MSG"));
			}
		}
	}

	@Override
	public String getName() {
		return "YSampleCallsSMSWithUtils";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleCallsSMSWithUtils();
	}
}