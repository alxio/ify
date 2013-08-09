package pl.poznan.put.cs.ify.base;

import pl.poznan.put.cs.ify.api.UninitializedException;
import pl.poznan.put.cs.ify.api.types.YParams;


public abstract class YReceipt {
	protected YParams mParams;
	public YReceipt(YParams params){
		mParams = params;
	}
	public abstract void handleTrigger(YTrigger trigger)throws UninitializedException;
}
