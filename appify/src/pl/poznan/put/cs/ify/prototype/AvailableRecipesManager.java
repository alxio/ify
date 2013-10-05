package pl.poznan.put.cs.ify.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.appify.receipts.SMSReceiptInfo;
import pl.poznan.put.cs.ify.core.YReceiptInfo;

public class AvailableRecipesManager {
	private List<YReceiptInfo> mReceiptInfos = new ArrayList<YReceiptInfo>();

	public AvailableRecipesManager() {
		initPrototypeData();
	}

	private void initPrototypeData() {
		mReceiptInfos.add(new SMSReceiptInfo());
		// mReceiptInfos.add(SMSReceiptInfo.getInstance());
		// mReceiptInfos.add(new YReceiptInfo("WifiInJob", null, null));
		// mReceiptInfos
		// .add(new YReceiptInfo("WifiOffWhenLowBattery", null, null));
	}

	public List<YReceiptInfo> getAvailableReceipesList() {
		return Collections.unmodifiableList(mReceiptInfos);
	}
}
