package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.params.YParamList;


/**
 * Simple recipe, that discards all incoming calls.
 */
public class YSampleCalls extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Calls;
	}

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	public void handleEvent(YEvent event) {
		//we don't check event type, because we only have one feature - event is incoming call.
		
		//discard call
		mFeatures.getCalls().discardCurrentCall();
	}

	@Override
	public String getName() {
		return "YCallsSample";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleCalls();
	}
}