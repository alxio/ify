package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class TestJar extends YReceipt {

	@Override
	public void requestFeatures(YFeatureList features) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(YEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TestJar";
	}

	@Override
	public YReceipt newInstance() {
		// TODO Auto-generated method stub
		return new TestJar();
	}

}
