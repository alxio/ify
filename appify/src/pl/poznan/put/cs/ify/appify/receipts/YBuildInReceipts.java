package pl.poznan.put.cs.ify.appify.receipts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.api.YReceipt;

public class YBuildInReceipts {
	private List<YReceipt> mList = null;

	private void initList() {
		mList = new ArrayList<YReceipt>();
		mList.add(new YWifiOffWhenLowBattery());
		mList.add(new YSMSReceipt());
		mList.add(new YAwesomeDemoReceipt());
		mList.add(new YPlayAcceleration());
		mList.add(new GeocoderReceipt());
		mList.add(new YTimeRingerReceipt());
		mList.add(new YRC());

	}

	public List<YReceipt> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}
}
