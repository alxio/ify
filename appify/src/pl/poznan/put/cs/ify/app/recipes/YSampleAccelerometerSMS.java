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
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.events.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Sample recipe reading data from Accelerometer and Sending SMS if it's high
 * enough.
 */
public class YSampleAccelerometerSMS extends YRecipe {

	// flag preventing from sending multiple SMS
	private boolean alreadySend = false;

	@Override
	public long requestFeatures() {
		return Y.SMS | Y.Accelerometer;
	}

	@Override
	public void requestParams(YParamList params) {
		// String param with phone number of SMS recipient
		params.add("SEND_TO", YParamType.Number, "");
		// Integer param with value of squared acceleration which triggers
		// recipe
		params.add("MIN", YParamType.Integer, 10);
	}

	@Override
	public void init() {
	}

	@Override
	public void handleEvent(YEvent event) {
		// Ignore events not coming from Accelerometer
		if (event.getId() != Y.Accelerometer)
			return;
		// Cast event to gain access to details.
		YAccelerometerEvent e = (YAccelerometerEvent) event;
		// Get squared length of acceleration vector
		float grall = e.getVector().getLengthSquere();
		// Displays squared length of acceleration vector in Logs
		Log.d(grall + "");
		// Get param value
		int min = getParams().getInteger("MIN");
		// It acceleration is small enough and we didn't already send SMS...
		if (grall < min && !alreadySend) {
			// Set flag
			alreadySend = true;
			// Gets the feature that was requested before
			YSMSFeature smsFeature = getFeatures().getSMS();
			// Sends SMS to recipient from param with fancy text
			smsFeature.sendSMS(getParams().getString("SEND_TO"),
					"Oops, my phone has falled.");
		}
	}

	@Override
	public String getName() {
		return "YSampleAccelerometerSMS";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleAccelerometerSMS();
	}
}
