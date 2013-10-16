package pl.poznan.put.cs.ify.appify.receipts;

import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.api.features.YReceipt;

public class YBuildInReceipts {
	private List<YReceipt> mList = null;

	private void initList() {
		mList.add(new WifiOffWhenLowBattery());
	}

	public List<YReceipt> getList() {
		if (mList == null)
			initList();
		return Collections.unmodifiableList(mList);
	}

}
