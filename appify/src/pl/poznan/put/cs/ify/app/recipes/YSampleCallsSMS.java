/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
		return Y.Calls | Y.SMS;
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
			getFeatures().getCalls().discardCurrentCall();
			//send sms
			getFeatures().getSMS().sendSMS(phone, getParams().getString("MSG"));
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
