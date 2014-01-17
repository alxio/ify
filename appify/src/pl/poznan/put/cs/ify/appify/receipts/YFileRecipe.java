package pl.poznan.put.cs.ify.appify.receipts;

import java.io.File;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YFilesFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YFileRecipe extends YReceipt {

	@Override
	public long requestFeatures() {
		return Y.Time | Y.Files;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		YFilesFeature files = (YFilesFeature) mFeatures.get(Y.Files);
		File recipeDirectory = files.getRecipeDirectory(this, true);
		Log.d("FILE " + recipeDirectory.getAbsolutePath());
	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "YFileRecipe";
	}

	@Override
	public YReceipt newInstance() {
		return new YFileRecipe();
	}
}
