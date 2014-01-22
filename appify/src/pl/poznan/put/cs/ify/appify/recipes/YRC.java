package pl.poznan.put.cs.ify.appify.recipes;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YRC extends YRecipe {
	private YComm comm;

	@Override
	public void requestParams(YParamList params) {
		params.add("Group", YParamType.String, "test");
	}

	@Override
	public long requestFeatures() {
		return Y.Group | Y.Text;
	}

	@Override
	public void init() {
		YGroupFeature gf = (YGroupFeature) mFeatures.get(Y.Group);
		comm = gf.createPoolingComm(this, mParams.getString("Group"), 5);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Text) {
			YTextEvent te = (YTextEvent) event;
			comm.sendEvent("BROADCAST", 1, "text", new YParam(YParamType.String, te.getText()));
			comm.pool();
		}
		if (event.getId() == Y.Group) {
			YGroupEvent ge = (YGroupEvent) event;
			String message = ge.getData().getDataAsString("text");
			String sender = ge.getData().getUserData().getId();
			Log.i("" + "<" + sender + "> " + message);
			comm.pool();
		}
	}

	@Override
	public String getName() {
		return "YRC";
	}

	@Override
	public YRecipe newInstance() {
		return new YRC();
	}

}
