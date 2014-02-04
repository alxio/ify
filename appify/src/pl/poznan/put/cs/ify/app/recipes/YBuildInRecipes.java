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
	}

	public List<YRecipe> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}
}
