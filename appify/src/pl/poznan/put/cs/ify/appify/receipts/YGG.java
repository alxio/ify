package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YGG extends YReceipt {
	private YComm comm;
	String username;
	@Override
	public void requestParams(YParamList params) {
		params.add("Group", YParamType.String, "test");
		params.add("User", YParamType.String, "test2");
	}

	@Override
	public long requestFeatures() {
		return Y.Group | Y.Text;
	}

	@Override
	public void init() {
		YGroupFeature gf = (YGroupFeature) mFeatures.get(Y.Group);
		username = gf.getCurrentUser().name;
		comm = gf.createPoolingComm(this, mParams.getString("Group"), 5);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Text) {
			YTextEvent te = (YTextEvent) event;
			Log.d("" + "<" + username + "> " + te.getText());
			comm.sendEvent(mParams.getString("User"), 1, "text", new YParam(YParamType.String, te.getText()));
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
		return "YGG";
	}

	@Override
	public YReceipt newInstance() {
		return new YGG();
	}

}
