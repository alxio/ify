package pl.poznan.put.cs.ify.appify.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.api.YRecipe;

public class YBuildInRecipes {
	private List<YRecipe> mList = null;

	private void initList() {
		mList = new ArrayList<YRecipe>();
		mList.add(new YWifiOffWhenLowBattery());
		mList.add(new YSMSRecipe());
		mList.add(new YAwesomeDemoRecipe());
		mList.add(new YPlayAcceleration());
		mList.add(new GeocoderRecipe());
		mList.add(new YTimeRingerRecipe());
		mList.add(new YRC());
		mList.add(new YGG());
		mList.add(new YFindFriend());
		mList.add(new YFileRecipe());
		mList.add(new YDiscardCall());
	}

	public List<YRecipe> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}
}
