package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class Sample1 extends YReceipt {

	@Override
	public long requestFeatures() {
		return 0;
	}

	@Override
	public void requestParams(YParamList params) {

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sample1";
	}

	@Override
	public YReceipt newInstance() {
		return new Sample1();
	}
}
