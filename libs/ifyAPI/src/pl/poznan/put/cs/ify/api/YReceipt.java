package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.log.YLogger;
import pl.poznan.put.cs.ify.api.params.YParamList;

public abstract class YReceipt {
	protected YParamList mParams;
	protected YFeatureList mFeatures;
	private int mId = 0;
	private int mTimestamp;

	/**
	 * Wrapper to {@link pl.poznan.put.cs.ify.api.log.YLog} inserting tag
	 * connected with recipe.
	 */
	protected YLogger Log;

	/**
	 * @return bitmask with ID's of features used by recipe.
	 */
	public abstract long requestFeatures();

	/**
	 * Fill YParamList with specification of params, as names, types and
	 * default. values.
	 */
	public abstract void requestParams(YParamList params);

	/**
	 * Saves params into mParams, and features into mFeatures.
	 * 
	 * @param params
	 */
	public final boolean initialize(IYReceiptHost host, YParamList params, YFeatureList features, int id, int timestamp) {
		mParams = params;
		mFeatures = features;
		mId = id;
		mTimestamp = timestamp;
		Log = new YLogger(createTag(mId, getName()), host);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Called when recipe is enabled, can be used for some internal
	 * initialization after creating recipe.
	 */
	protected void init() throws Exception{

	}

	public final boolean tryHandleEvent(YEvent event){
		try {
			handleEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Called after event occurs, contains main receipt logic.
	 * 
	 * @param event
	 * @throws UninitializedException
	 */
	protected abstract void handleEvent(YEvent event) throws Exception;

	/**
	 * @return Name of the recipe.
	 */
	public abstract String getName();

	/**
	 * Should call default constructor.
	 */
	public abstract YReceipt newInstance();

	/**
	 * @return Features used by recipe.
	 */
	public YFeatureList getFeatures() {
		return mFeatures;
	}

	/**
	 * @return Parameters of this recipe's instance.
	 */
	public YParamList getParams() {
		return mParams;
	}

	/**
	 * @return Unique ID of receipt's instance generated by service.
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @return Time when recipe was initialized.
	 */
	public int getTimestamp() {
		return mTimestamp;
	}

	/**
	 * @return Tag used in logs, currently it's #ID: NAME
	 */
	public static String createTag(int id, String name) {
		return "#" + id + ": " + name;
	}

	@Override
	public String toString() {
		return createTag(mId, getName());
	}
}
