package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YTimeFeature;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YCommData;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YGroupReceiptTest extends YReceipt {

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YGroupFeature());
		features.add(new YTimeFeature());
	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(YEvent event) {
		YGroupFeature feature = (YGroupFeature) mFeatures.get(Y.Group);
		YComm comm = feature.createComm(this, "developers");
		comm.sendEvent("mateusz@1337", 1);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "YGroupReceiptTest";
	}

	@Override
	public YReceipt newInstance() {
		// TODO Auto-generated method stub
		return new YGroupReceiptTest();
	}

}
