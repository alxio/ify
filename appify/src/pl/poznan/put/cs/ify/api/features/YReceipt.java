package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YTrigger;
import pl.poznan.put.cs.ify.api.exceptions.UninitializedException;
import pl.poznan.put.cs.ify.params.YParamList;

public abstract class YReceipt {
	protected YParamList mParams;
	protected YFeatureList mFeatures;

	/**
	 * Fill YFeatureList with specification of needed features, as new (blank)
	 * objects.
	 */
	public abstract void requestFeatures(YFeatureList features);

	/**
	 * Fill YParamList with specification of params, as names, types and default values.
	 */
	public abstract void requestParams(YParamList params);

	/**
	 * Saves params into mParams, and features into mFeatures, insert other code
	 * needed to initialize receipt here.
	 * 
	 * @param params
	 */
	public void initialize(YParamList params, YFeatureList features) {
		mParams = params;
		mFeatures = features;
	}

	/**
	 * Called after trigger occurs, contains main receipt logic.
	 * 
	 * @param trigger
	 * @throws UninitializedException
	 */
	public abstract void handleTrigger(YTrigger trigger)
			throws UninitializedException;

	public abstract String getName();

	public YFeatureList getFeatures() {
		return mFeatures;
	}
}
