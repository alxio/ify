package pl.poznan.put.cs.ify.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.android.jars.JarInfo;
import pl.poznan.put.cs.ify.android.jars.JarOpener;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.appify.receipts.SMSReceiptInfo;
import pl.poznan.put.cs.ify.appify.receipts.YBuildInReceipts;
import pl.poznan.put.cs.ify.core.YReceiptInfo;
import android.content.Context;
import android.os.Bundle;

public class AvailableRecipesManager {
	private List<YReceiptInfo> mReceiptInfos = new ArrayList<YReceiptInfo>();
	private Map<String, YReceipt> mAvaibleRecipes = new HashMap<String, YReceipt>();
	private YBuildInReceipts mBuildInList = new YBuildInReceipts();

	public AvailableRecipesManager(Context ctx) {
		loadSampleJar(ctx, "MyReceipt");
		loadSampleJar(ctx, "SecondReceipt");
		initPrototypeData();
		loadBuildIn();
	}

	@Deprecated
	public List<YReceiptInfo> getAvailableReceipesList() {
		return Collections.unmodifiableList(mReceiptInfos);
	}

	public Map<String, YReceipt> getAvailableReceipesMap() {
		return Collections.unmodifiableMap(mAvaibleRecipes);
	}

	public YReceipt get(String name) {
		return mAvaibleRecipes.get(name);
	}

	private void loadBuildIn() {
		for (YReceipt receipt : mBuildInList.getList()) {
			mReceiptInfos.add(new YReceiptInfo(receipt));
			mAvaibleRecipes.put(receipt.getName(), receipt);
		}
	}

	private void initPrototypeData() {
		mReceiptInfos.add(new SMSReceiptInfo());
		// mReceiptInfos.add(SMSReceiptInfo.getInstance());
		// mReceiptInfos.add(new YReceiptInfo("WifiInJob", null, null));
		// mReceiptInfos
		// .add(new YReceiptInfo("WifiOffWhenLowBattery", null, null));
	}

	private void loadSampleJar(Context ctx, String name) {
		JarOpener opener = new JarOpener();
		JarInfo jarInfo = new JarInfo(name, "/mnt/sdcard/ify/" + name + ".jar");
		YReceipt receipt = opener.openJar(ctx, jarInfo);
		if (receipt != null) {
			mReceiptInfos.add(new YReceiptInfo(receipt));
			mAvaibleRecipes.put(receipt.getName(), receipt);
		}
	}
}
