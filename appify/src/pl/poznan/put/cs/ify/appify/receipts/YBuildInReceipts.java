package pl.poznan.put.cs.ify.appify.receipts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.api.YReceipt;

public class YBuildInReceipts {
	private List<YReceipt> mList = null;

	private void initList() {
		mList = new ArrayList<YReceipt>();
		mList.add(new WifiOffWhenLowBattery());
		mList.add(new SMSReceipt());
		mList.add(new AwesomeDemoReceipt());
	}

	public List<YReceipt> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}

}
