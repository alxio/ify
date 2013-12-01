package pl.poznan.put.cs.ify.api;

import java.util.HashSet;
import java.util.Set;

public abstract class YFeature {
	protected IYReceiptHost mHost = null;

	public abstract long getId();

	public void initialize(IYReceiptHost host, IYReceiptHost srv) {
		mHost = host;
		init(srv);
	}

	protected abstract void init(IYReceiptHost srv);

	public abstract void uninitialize();

	public void registerReceipt(YReceipt receipt) {
		mListeners.add(receipt);
	}

	public void unregisterReceipt(YReceipt receipt) {
		mListeners.remove(receipt);
	}

	public boolean isUsed() {
		return !mListeners.isEmpty();
	}

	private Set<YReceipt> mListeners = new HashSet<YReceipt>();

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
