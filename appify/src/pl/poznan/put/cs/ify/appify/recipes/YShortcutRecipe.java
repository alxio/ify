package pl.poznan.put.cs.ify.appify.recipes;

import android.widget.Toast;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.YShortcutFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YShortcutRecipe extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Shortcut | Y.SMS;
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
		params.add("NUMBER", YParamType.Number, "");
		params.add("MESSAGE", YParamType.String, "");

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		if (event.getId() == Y.Shortcut) {
			YSMSFeature sms = mFeatures.getSMS();
			sms.sendSMS(mParams.getNumber("NUMBER"),
					mParams.getString("MESSAGE"));
		}
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
