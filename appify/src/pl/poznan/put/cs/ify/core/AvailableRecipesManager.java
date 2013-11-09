package pl.poznan.put.cs.ify.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.appify.receipts.YBuildInReceipts;
import pl.poznan.put.cs.ify.jars.JarDatabaseOpenHelper;
import pl.poznan.put.cs.ify.jars.JarInfo;
import pl.poznan.put.cs.ify.jars.JarOpener;
import android.content.Context;

public class AvailableRecipesManager {
	private Map<String, YReceipt> mAvaibleRecipes = new HashMap<String, YReceipt>();
	private YBuildInReceipts mBuildInList = new YBuildInReceipts();
	private Context mContext;

	public AvailableRecipesManager(Context ctx) {
		// loadSampleJar(ctx, "MyReceipt");
		// loadSampleJar(ctx, "SecondReceipt");
		// loadSampleJar(ctx, "SconyReceipt");
		//loadSampleJar(ctx, "YBadumReceipt");
		mContext = ctx;
		loadBuildIn();
//		loadFromJars();

	}

	private void loadFromJars() {
		JarDatabaseOpenHelper db = new JarDatabaseOpenHelper(mContext);
		List<JarInfo> jars = db.getJars();
		for (JarInfo jarInfo : jars) {
			loadSampleJar(mContext, jarInfo.getClassName());
		}
		db.close();
	}

	public Map<String, YReceipt> getAvailableReceipesMap() {
		return Collections.unmodifiableMap(mAvaibleRecipes);
	}

	public YReceipt get(String name) {
		return mAvaibleRecipes.get(name);
	}

	private void loadBuildIn() {
		for (YReceipt receipt : mBuildInList.getList()) {
			mAvaibleRecipes.put(receipt.getName(), receipt);
		}
	}

	private void loadSampleJar(Context ctx, String name) {
		JarOpener opener = new JarOpener();
		YReceipt receipt = opener.openJar(ctx, name);
		if (receipt != null) {
			mAvaibleRecipes.put(receipt.getName(), receipt);
		}
	}

	public void refresh() {
		mAvaibleRecipes.clear();
		loadBuildIn();
		loadFromJars();
		loadSampleJar(mContext, "YBadumReceipt");
	}
}
