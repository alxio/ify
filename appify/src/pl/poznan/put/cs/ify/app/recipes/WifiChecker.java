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
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent;
import pl.poznan.put.cs.ify.api.features.events.YWifiEvent.WiFi_EventType;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class WifiChecker extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Wifi | Y.Notification;
	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		if (event.getId() == Y.Wifi) {
			YWifiEvent we = (YWifiEvent) event;
			Log.i(we.getEventType().name());
			if (we.getEventType() == WiFi_EventType.DISCONNECTED) {
				getFeatures().getNotification().createNotification(
						"connection lost", this);
			}
		}
	}

	@Override
	public String getName() {
		return "WifiChecker";
	}

	@Override
	public YRecipe newInstance() {
		return new WifiChecker();
	}

}
