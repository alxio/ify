package pl.poznan.put.cs.ify.appify.recipes;

import android.widget.Toast;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YShortcutFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YShortcutRecipe extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Shortcut;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		YShortcutFeature yFeature = (YShortcutFeature) mFeatures
				.get(Y.Shortcut);
		yFeature.createShortcut(this, "TEST");
	}

	@Override
	public void requestParams(YParamList params) {

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		throw new RuntimeException();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "YShortcutRecipe";
	}

	@Override
	public YRecipe newInstance() {
		// TODO Auto-generated method stub
		return new YShortcutRecipe();
	}

}
