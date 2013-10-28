package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAccelerometerEvent;
import pl.poznan.put.cs.ify.api.features.YBatteryEvent;
import pl.poznan.put.cs.ify.api.features.YSMSEvent;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class MyReceipt extends YReceipt {

	@Override
	public void handleEvent(YEvent event) {

		if(event.getId() == Y.Accelerometer){
			YAccelerometerEvent e = (YAccelerometerEvent) event;
			//TODO: Insert your code here
		}
		
		if(event.getId() == Y.Battery){
			YBatteryEvent e = (YBatteryEvent) event;
			//TODO: Insert your code here
		}
		
		if(event.getId() == Y.SMS){
			YSMSEvent e = (YSMSEvent) event;
			//TODO: Insert your code here
		}
		
	}

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YSMSFeature());
	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		//Generated code, don't change.
		return "MyReceipt";
	}

	@Override
	public YReceipt newInstance() {
		//Generated code, don't change.
		return new MyReceipt();
	}
}