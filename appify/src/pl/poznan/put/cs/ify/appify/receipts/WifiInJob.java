package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.UninitializedException;
import pl.poznan.put.cs.ify.api.YPositionHelper;
import pl.poznan.put.cs.ify.api.YTimerTrigger;
import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.api.YWifi;
import pl.poznan.put.cs.ify.api.types.YParam.Type;
import pl.poznan.put.cs.ify.api.types.YParamList;
import pl.poznan.put.cs.ify.base.YReceipt;

public class WifiInJob extends YReceipt{
	
	//TODO: chyba to nie powinno byæ tutaj polem, tylko byæ jakoœ m¹drzej zarz¹dzane...
	private YTimerTrigger mTimer;
	
	@Override
	public YParamList getParams() {
		YParamList params = new YParamList();
		params.add("WorkPosition", Type.Position);
		return params;
	}
	@Override
	public void initialize(YParamList params) {
		mTimer = new YTimerTrigger(30);
		mTimer.register(this);
	}
	@Override
	public void handleTrigger(YTrigger trigger) throws UninitializedException {
		double dist = YPositionHelper.getDistance(mParams.getPosition("WorkPosition"));
		//don't change between 10 and 11 to avoid glimmering
		if(dist < 10) YWifi.enable();
		if(dist > 11) YWifi.disable();
	}

}
