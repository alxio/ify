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

import java.util.Date;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YAudioManagerFeature;
import pl.poznan.put.cs.ify.api.features.events.YTimeEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YTimeRingerRecipe extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Time | Y.AudioManager;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("SILENT_FROM", YParamType.Integer, 0);
		params.add("SILENT_TO", YParamType.Integer, 8);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Time) {
			YTimeEvent timeEvent = (YTimeEvent) event;
			Date date = timeEvent.getDate();
			@SuppressWarnings("deprecation")
			int hours = date.getHours();
			YAudioManagerFeature audioManager = (YAudioManagerFeature) getFeatures().get(Y.AudioManager);
			int silentFrom = getParams().getInteger("SILENT_FROM");
			int silentTo = getParams().getInteger("SILENT_TO");
			if (hours >= silentFrom && hours < silentTo) {
				audioManager.setSilent();
			} else {
				audioManager.setNormal();
			}
		}
	}

	@Override
	public String getName() {
		return "YTimeRingerRecipe";
	}

	@Override
	public YRecipe newInstance() {
		return new YTimeRingerRecipe();
	}

}
