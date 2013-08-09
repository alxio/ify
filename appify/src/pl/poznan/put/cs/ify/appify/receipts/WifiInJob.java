package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.UninitializedException;
import pl.poznan.put.cs.ify.api.YPositionHelper;
import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.api.YWifi;
import pl.poznan.put.cs.ify.api.types.YFeatureList;
import pl.poznan.put.cs.ify.api.types.YParam.Type;
import pl.poznan.put.cs.ify.api.types.YParamList;
import pl.poznan.put.cs.ify.base.YReceipt;

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
