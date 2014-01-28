package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class Sample extends YRecipe {
	private YComm comm;

	@Override
	public void requestParams(YParamList params) {
		params.add("Delay", new YParam(YParamType.Integer, 5));
	}

	@Override
	public long requestFeatures() {
		return Y.Text | Y.Group;
	}

	@Override
	public void init() {
		comm = ((YGroupFeature) mFeatures.get(Y.Group)).createPoolingComm(this,
				"developers", mParams.getInteger("Delay"));
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Text) {
			comm.sendEvent("BROADCAST", 1, "text", new YParam(
					YParamType.String, ((YTextEvent) event).getText()));
		}
	}

	@Override
	public String getName() {
		return "Sample";
	}

	@Override
	public YRecipe newInstance() {
		return new Sample();
	}
}
