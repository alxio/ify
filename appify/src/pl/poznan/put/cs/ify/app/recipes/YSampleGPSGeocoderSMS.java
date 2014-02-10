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

import java.util.HashMap;
import java.util.HashSet;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.events.YGeocoderEvent;
import pl.poznan.put.cs.ify.api.features.events.YSMSEvent;
import pl.poznan.put.cs.ify.api.params.YLocation;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Recipe activated by receiving SMS with given text (MESSAGE param) reading location from GPS, converting it to address and sending SMS.
 */
public class YSampleGPSGeocoderSMS extends YRecipe {
	//time when given number requested position to avoid spam.
	private HashMap<String, Long> mLastQueryTime = new HashMap<String, Long>();
	//set of numbers waiting for location.
	private HashSet<String> mPending = new HashSet<String>();

	@Override
	public long requestFeatures() {
		return Y.GPS | Y.Geocoder | Y.SMS;
	}

	@Override
	public void requestParams(YParamList params) {
		//message activating recipe
		params.add("NUMBER", YParamType.String, "MESSAGE");
	}
	
	private void requestGeocoding() {
		//take last GPS position
		YLocation loc = getFeatures().getGPS().getLastLocation();
		if (loc != null) {
			//TODO: add requstAddress taking YLocation as argument
			//request geocoder to decode GPS location
			getFeatures().getGeocoder().requestAddress(loc.getLatitude(), loc.getLongitude());
		}
	}

	@Override
	public void handleEvent(YEvent event) {
		//We know our location
		if (event.getId() == Y.Geocoder) {
			//take address from event
			String addres = ((YGeocoderEvent) event).getAddress();
			//take SMS feature
			YSMSFeature smsFeature = getFeatures().getSMS();
			//send SMS with location to all pending contacts
			for (String rec : mPending) {
				//we ignore case when new SMS comes during this loop causing ConcurrentModificationException
				smsFeature.sendSMS(rec, addres);
			}
			mPending.clear();
		}
		//We got SMS
		else if (event.getId() == Y.SMS) {
			YSMSEvent smsEvent = (YSMSEvent) event;
			//extract sender
			String sender = smsEvent.getSender();
			//check if it's our secret message
			if (smsEvent.getMessage().equals(getParams().getString("MESSAGE"))){
				long currentTime = System.currentTimeMillis();
				//get time of last query from this sender
				Long lastTime = mLastQueryTime.get(sender);
				//only respond if person didn't send any request before or sent it more than half hour ago (30*60*1000 ms)
				if (lastTime == null || currentTime < lastTime + 30 * 60 * 1000) {
					//add sender to pending ones
					mPending.add(sender);
					requestGeocoding();
					//save time when sender requested position
					mLastQueryTime.put(sender, currentTime);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "YGPSGeocoderSMSSample";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleGPSGeocoderSMS();
	}

}
      
