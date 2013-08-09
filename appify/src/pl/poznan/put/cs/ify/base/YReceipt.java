package pl.poznan.put.cs.ify.base;

import pl.poznan.put.cs.ify.api.UninitializedException;
import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.api.types.YFeatureList;
import pl.poznan.put.cs.ify.api.types.YParamList;


public abstract class YReceipt {
	protected YParamList mParams;
	protected YFeatureList mFeatures;
	/**
	 * @return Specification of needed features, as YFeatureList with new (blank) objects.
	 */
	public abstract YFeatureList getFeatures();
	/**
	 * @return Specification of params, as YParamList with names and types (no values).
	 */
	public abstract YParamList getParams();
	/**
	 * Saves params into mParams, and features into mFeatures,
	 * insert other code needed to initialize receipt here.
	 * @param params
	 */
	public void initialize(YParamList params, YFeatureList features){
		mParams = params;
		mFeatures = features;
	}
	/**
	 * Called after trigger occurs, contains main receipt logic.
	 * @param trigger
	 * @throws UninitializedException
	 */
	public abstract void handleTrigger(YTrigger trigger)throws UninitializedException;
}
