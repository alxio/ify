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


/**
 * Simple recipe, that discards all incoming calls.
 */
public class YSampleCalls extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Calls;
	}

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public void handleEvent(YEvent event) {
		//we don't check event type, because we only have one feature - event is incoming call.
		
		//discard call
		getFeatures().getCalls().discardCurrentCall();
	}

	@Override
	public String getName() {
		return "YCallsSample";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleCalls();
	}
}
