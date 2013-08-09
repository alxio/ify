package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.UninitializedException;
import pl.poznan.put.cs.ify.api.YPositionHelper;
import pl.poznan.put.cs.ify.api.YTimerTrigger;
import pl.poznan.put.cs.ify.api.YWifi;
import pl.poznan.put.cs.ify.api.types.YParams;
import pl.poznan.put.cs.ify.base.YReceipt;
import pl.poznan.put.cs.ify.base.YTrigger;

public class WifiInJob extends YReceipt{
	
	//TODO: chyba to nie powinno by� tutaj polem, tylko by� jako� m�drzej zarz�dzane...
	private YTimerTrigger mTimer;
	
	public WifiInJob(YParams params) {
		super(params);
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
