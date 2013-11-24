package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.log.YLogger;
import pl.poznan.put.cs.ify.api.params.YParamList;

public abstract class YReceipt {
	protected YParamList mParams;
	protected YFeatureList mFeatures;
	private int mId = 0;
	private int mTimestamp;

	protected YLogger Log;

	/**
	 * Fill YFeatureList with specification of needed features, as new (blank)
	 * objects.
	 */
	public abstract long requestFeatures();

	/**
	 * Fill YParamList with specification of params, as names, types and default
	 * values.
	 */
	public abstract void requestParams(YParamList params);

	/**
	 * Saves params into mParams, and features into mFeatures.
	 * 
	 * @param params
	 */
	public final void initialize(IYReceiptHost host, YParamList params, YFeatureList features, int id, int timestamp) {
		mParams = params;
		mFeatures = features;
		mId = id;
		mTimestamp = timestamp;
		Log = new YLogger(createTag(mId, getName()), host);
		init();
	}

	/**
	 * Override it if you need some initialization after creating recipe
	 */
	protected void init() {

	}

	/**
	 * Called after trigger occurs, contains main receipt logic.
	 * 
	 * @param trigger
	 * @throws UninitializedException
	 */
	public abstract void handleEvent(YEvent event);

	public abstract String getName();

	public abstract YReceipt newInstance();

	public YFeatureList getFeatures() {
		return mFeatures;
	}

	public YParamList getParams() {
		return mParams;
	}

	public int getId() {
		return mId;
	}

	public int getTimestamp() {
		return mTimestamp;
	}

	public static String createTag(int id, String name) {
		return "#" + id + ": " + name;
	}

	@Override
	public String toString() {
		return createTag(mId, getName());
	}
}
