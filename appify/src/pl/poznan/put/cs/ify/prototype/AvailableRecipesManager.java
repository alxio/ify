package pl.poznan.put.cs.ify.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.poznan.put.cs.ify.core.YReceiptInfo;

public class AvailableRecipesManager {
	private List<YReceiptInfo> mReceiptInfos = new ArrayList<YReceiptInfo>();

	public AvailableRecipesManager() {
		initPrototypeData();
	}

	private void initPrototypeData() {
		mReceiptInfos.add(new YReceiptInfo("WifiInJob"));
		mReceiptInfos.add(new YReceiptInfo("WifiOffWhenLowBattery"));
	}

	public List<YReceiptInfo> getAvailableReceipesList() {
		return Collections.unmodifiableList(mReceiptInfos);
	}
}
