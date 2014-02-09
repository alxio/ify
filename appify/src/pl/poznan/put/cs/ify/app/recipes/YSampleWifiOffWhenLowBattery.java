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
import pl.poznan.put.cs.ify.api.features.events.YBatteryEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Sample recipe turning off WiFi connection when battery is low.
 */
public class YSampleWifiOffWhenLowBattery extends YRecipe {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", YParamType.Integer, 90);
	}

	@Override
	public long requestFeatures() {
		return Y.Battery | Y.Wifi;
	}

	@Override
	public String getName() {
		return "WifiOffWhenLowBattery";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleWifiOffWhenLowBattery();
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Battery) {
			YBatteryEvent e = (YBatteryEvent) event;
			// print battery percentage to logs
			Log.i(e.getLevel() + "");
			// if battery is low...
			if (e.getLevel() < getParams().getInteger("Level")) {
				// ...disable wifi
				getFeatures().getWifi().disable();
			}
		}
	}
}
