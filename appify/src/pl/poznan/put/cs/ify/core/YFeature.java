package pl.poznan.put.cs.ify.core;

import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;

public abstract class YFeature {
	/**
	 * 
	 * @return class name
	 */
	public abstract String getName(); 
	public abstract void initialize(Context ctx, YReceiptsService srv); //TODO: Remove first arg maybe
	public abstract void registerReceipt(YReceipt receipt);
	
	private int mReceiptsCount;
	public void addReceipt(YReceipt receipt){
		mReceiptsCount++;
		registerReceipt(receipt);
	}
	public boolean isUsed(){
		return mReceiptsCount > 0;
	}
	public void removeUser(){
		mReceiptsCount--;
	}
	
}
