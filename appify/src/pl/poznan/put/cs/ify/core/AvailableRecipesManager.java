package pl.poznan.put.cs.ify.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.app.YReceiptInfo;
import pl.poznan.put.cs.ify.appify.receipts.YBuildInReceipts;
import pl.poznan.put.cs.ify.jars.JarOpener;
import android.content.Context;

public class AvailableRecipesManager {
	private List<YReceiptInfo> mReceiptInfos = new ArrayList<YReceiptInfo>();
	private Map<String, YReceipt> mAvaibleRecipes = new HashMap<String, YReceipt>();
	private YBuildInReceipts mBuildInList = new YBuildInReceipts();

	public AvailableRecipesManager(Context ctx) {
		loadSampleJar(ctx, "MyReceipt");
		loadSampleJar(ctx, "SecondReceipt");
		loadSampleJar(ctx, "SconyReceipt");
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

	private void loadSampleJar(Context ctx, String name) {
		JarOpener opener = new JarOpener();
		YReceipt receipt = opener.openJar(ctx, name);
		if (receipt != null) {
			mReceiptInfos.add(new YReceiptInfo(receipt));
			mAvaibleRecipes.put(receipt.getName(), receipt);
		}
	}
}
