package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YCallsFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YDiscardCall extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Calls;
	}

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Calls) {
			YCallsFeature callsFeature = mFeatures.getCalls();
			callsFeature.discardCurrentCall();
		}
	}

	@Override
	public String getName() {
		return "YDiscardCall";
	}

	@Override
	public YRecipe newInstance() {
		return new YDiscardCall();
	}
}
