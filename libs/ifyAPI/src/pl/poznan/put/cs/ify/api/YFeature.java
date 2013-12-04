package pl.poznan.put.cs.ify.api;

import java.util.HashSet;
import java.util.Set;

public abstract class YFeature {
	// TODO: Weak reference
	protected IYReceiptHost mHost = null;
	protected Set<YReceipt> mListeners = new HashSet<YReceipt>();

	/**
	 * @return Id of feature, it's one bit and can be used in bitmasks.
	 */
	public abstract long getId();

	/**
	 * Called by host to initialize feature.
	 * 
	 * @param host
	 */
	public void initialize(IYReceiptHost host) {
		mHost = host;
		init(mHost);
	}

	/**
	 * Internal initialization of specific feature.
	 * 
	 * @param host
	 */
	protected abstract void init(IYReceiptHost srv);

	/**
	 * Internal uninitialization of specific feature.
	 * 
	 * @param host
	 */
	public abstract void uninitialize();

	/**
	 * Called when initializing recipe using this feature.
	 * 
	 * @param receipt
	 */
	public void registerReceipt(YReceipt receipt) {
		mListeners.add(receipt);
	}

	/**
	 * Called when uninitializing recipe using this feature.
	 * 
	 * @param receipt
	 */
	public void unregisterReceipt(YReceipt receipt) {
		mListeners.remove(receipt);
	}

	/**
	 * @return true if any recipe uses it.
	 */
	public boolean isUsed() {
		return !mListeners.isEmpty();
	}

	/**
	 * Sends event to all recipes using this feature.
	 * @see pl.poznan.put.cs.ify.api.YReceipt#handleEvent(YEvent)
	 * @param event
	 */
	public void sendNotification(YEvent event) {
		Set<YReceipt> toDelete = new HashSet<YReceipt>();
		for (YReceipt receipt : mListeners) {
			if (receipt != null)
				receipt.handleEvent(event);
			else
				toDelete.add(receipt);
		}
		mListeners.removeAll(toDelete);
	}
}
