package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.base.YParams;
import pl.poznan.put.cs.ify.base.YReceipt;
import pl.poznan.put.cs.ify.base.YTrigger;
import pl.poznan.put.cs.ify.position.YTimerTrigger;
import pl.poznan.put.cs.ify.position.YPosition;
import pl.poznan.put.cs.ify.position.YPositionHelper;

public class WifiInJob extends YReceipt{
	
	//TODO: Mo¿e to nie powinno byæ tutaj polem, tylko byæ jakoœ m¹drzej zarz¹dzane...
	private YTimerTrigger mTimer;
	
	public WifiInJob(YParams params) {
		super(params);
		mTimer = new YTimerTrigger(30);
		mTimer.register(this);
	}
	@Override
	public void handleTrigger(YTrigger trigger) {
		double dist = YPositionHelper.getDistance((YPosition) mParams.getValue("WorkPosition"));
		//don't change between 10 and 11 to avoid glimmering
		if(dist < 10) enableWifi();
		if(dist > 11) disableWifi();
	}
	private void disableWifi() {
		// TODO Auto-generated method stub
		
	}
	private void enableWifi() {
		// TODO Auto-generated method stub
		
	}
}
