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
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Sample recipe doing nothing
 */
public class YSampleEmptyRecipe extends YRecipe {
	@Override
	public void requestParams(YParamList params) {
		params.add("Level", YParamType.Integer, 90);
	}
	
	@Override
	protected void init(){
	 // It's called when recipe is enabled
	 // Can be used for some internal initialization after creating recipe.
	 // Initialize your internal fields here.
	}

	@Override
	public long requestFeatures() {
	 // Return bitmask with ID's of features used by recipe.
	 // Use ID's from Y class and OR ('|') operator.
		return Y.Battery | Y.Wifi;
	}

	@Override
	public void handleEvent(YEvent event) {
	 // It's place for main recipe logic.
	 // It's called on event connected with requested feature occurs.
	 // Check what event is that by checking event.getId()
	 // and compare it to id's from Y.
	 // Later you will propably need to cast it like that:
	 
		// if (event.getId() == Y.Battery) {
		//	YBatteryEvent e = (YBatteryEvent) event;
		//	}
		// }
	}
	
	@Override
	public String getName() {
	 // Return String same like your recipe class name here.
	 // It's needed to avoid using reflection which is slow.
	 // It should be generated my market.
		return "YEmptyRecipe";
	}

	@Override
	public YRecipe newInstance() {
	 // Just call default constructor and return recipe instance.
	 // Also needed to avoid reflection.
	 // It should be generated my market.
		return new YSampleEmptyRecipe();
	}
}
