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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.api.YRecipe;

public class YBuildInRecipes {
	private List<YRecipe> mList = null;

	private void initList() {
		mList = new ArrayList<YRecipe>();

		// TODO: add them back when renamed, commented and uploaded into market.
		// mList.add(new YSMSRecipe());
		// mList.add(new GeocoderRecipe());
		// mList.add(new YTimeRingerRecipe());
		// mList.add(new YFileRecipe());
		// mList.add(new YShortcutRecipe());

		mList.add(new YSampleAccelerometerNotification());
		mList.add(new YSampleAccelerometerSMS());
		mList.add(new YSampleCalls());
		mList.add(new YSampleEmptyRecipe());
		mList.add(new YSampleFindFriend());
		mList.add(new YSampleGPSGeocoderSMS());
		mList.add(new YSampleGroupSMS());
		mList.add(new YSampleRawPlayerAccelerometer());
		mList.add(new YSampleWifiOffWhenLowBattery());
		mList.add(new YSampleYRC());
		mList.add(new YSampleCallsSMSWithUtils());
		mList.add(new YSampleCallsSMS());
		mList.add(new YSampleGPSGroup());
		mList.add(new WifiChecker());
	}

	public List<YRecipe> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}
}
