package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;

public abstract class YReceipt {
	protected YParamList mParams;
	protected YFeatureList mFeatures;

	/**
	 * Fill YFeatureList with specification of needed features, as new (blank)
	 * objects.
	 */
	public abstract void requestFeatures(YFeatureList features);

	/**
	 * Fill YParamList with specification of params, as names, types and default
	 * values.
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
	// TODO : Change bundle to YBudnle or something like that.
	public abstract void handleData(YFeature feature, Bundle data);

	public abstract String getName();

	public abstract YReceipt newInstance();

	public YFeatureList getFeatures() {
		return mFeatures;
	}

	public YParamList getParams() {
		return mParams;
	}
}
