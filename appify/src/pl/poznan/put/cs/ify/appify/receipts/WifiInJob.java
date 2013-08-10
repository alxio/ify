package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.core.UninitializedException;
import pl.poznan.put.cs.ify.core.YFeatureList;
import pl.poznan.put.cs.ify.core.YReceipt;
import pl.poznan.put.cs.ify.core.YTrigger;
import pl.poznan.put.cs.ify.features.YPositionHelper;
import pl.poznan.put.cs.ify.features.YWifi;
import pl.poznan.put.cs.ify.params.YParam.Type;
import pl.poznan.put.cs.ify.params.YParamList;

public class WifiInJob extends YReceipt{
	@Override
	public void requestParams(YParamList params) {
		params.add("WorkPosition", Type.YPosition);
	}
	@Override
	public void requestFeatures(YFeatureList feats) {
		feats.add(new YWifi());
	}
	@Override
	public void initialize(YParamList params, YFeatureList features) {
		
		//TODO: Add trigger
	}
	@Override
	public void handleTrigger(YTrigger trigger) throws UninitializedException {
		//TODO: maybe change when we will have GPS
		double dist = YPositionHelper.getDistance(mParams.getPosition("WorkPosition"));
		
		//don't change between 10 and 11 to avoid glimmering
		
		if(dist < 10) {
			//example of specific getter
			mFeatures.getWifi().enable();  
		}
		if(dist > 11) {
			//example of usage with casting
			((YWifi)mFeatures.get("YWifi")).disable();
		}
	}
}
