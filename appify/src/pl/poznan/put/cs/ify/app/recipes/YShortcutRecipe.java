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
import pl.poznan.put.cs.ify.api.features.YShortcutFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YShortcutRecipe extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Shortcut | Y.SMS;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		YShortcutFeature yFeature = (YShortcutFeature) getFeatures()
				.get(Y.Shortcut);
		yFeature.createShortcut(this, "TEST");
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("NUMBER", YParamType.Number, "");
		params.add("MESSAGE", YParamType.String, "");

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		if (event.getId() == Y.Shortcut) {
			YSMSFeature sms = getFeatures().getSMS();
			sms.sendSMS(getParams().getNumber("NUMBER"),
					getParams().getString("MESSAGE"));
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "YShortcutRecipe";
	}

	@Override
	public YRecipe newInstance() {
		// TODO Auto-generated method stub
		return new YShortcutRecipe();
	}

}
