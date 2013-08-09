package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.UninitializedException;
import pl.poznan.put.cs.ify.YReceipt;
import pl.poznan.put.cs.ify.YTrigger;
import pl.poznan.put.cs.ify.features.YFeatureList;
import pl.poznan.put.cs.ify.features.YPositionHelper;
import pl.poznan.put.cs.ify.features.YWifi;
import pl.poznan.put.cs.ify.params.YParamList;
import pl.poznan.put.cs.ify.params.YParam.Type;

public class WifiInJob extends YReceipt{
	@Override
	public YParamList getParams() {
		YParamList params = new YParamList();
		params.add("WorkPosition", Type.Position);
		return params;
	}
	@Override
	public YFeatureList getFeatures() {
		YFeatureList feats = new YFeatureList();
		feats.add(new YWifi());
		return feats;
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
