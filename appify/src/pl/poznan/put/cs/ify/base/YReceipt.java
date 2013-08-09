package pl.poznan.put.cs.ify.base;

import pl.poznan.put.cs.ify.api.UninitializedException;
import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.api.types.YFeatureList;
import pl.poznan.put.cs.ify.api.types.YParamList;


public abstract class YReceipt {
	protected YParamList mParams;
	/**
	 * @return Specification of params, as ParamList with names and types (no values).
	 */
	public abstract YParamList getParams();
	/**
	 * Saves params into mParams, insert other code needed to initialize receipt here.
	 * @param params
	 */
	public void initialize(YParamList params){
		mParams = params;
	}
	/**
	 * Called after trigger occurs, contains main receipt logic.
	 * @param trigger
	 * @throws UninitializedException
	 */
	public abstract void handleTrigger(YTrigger trigger)throws UninitializedException;
}
