package pl.poznan.put.cs.ify.core;

import java.util.HashSet;
import java.util.Set;

import pl.poznan.put.cs.ify.features.YReceipt;

public abstract class YTrigger {
	private Set<YReceipt> mListeners = new HashSet<YReceipt>();

	public void register(YReceipt receipt) {
		mListeners.add(receipt);
	}

	public void sendNotification() throws UninitializedException {
		Set<YReceipt> toDelete = new HashSet<YReceipt>();
		for (YReceipt receipt : mListeners) {
			if (receipt != null)
				receipt.handleTrigger(this);
			else
				toDelete.add(receipt);
		}
		mListeners.removeAll(toDelete);
	}

	public void trySendNotification() {
		try {
			sendNotification();
		} catch (UninitializedException e) {
			// TODO: handle it, let user know someone created bad receipt.
		}
	}

	// Wo³ane przy niejednorazowych triggerach po niszczeniu recepty, w zasadzie
	// mo¿na pomin¹æ, jeœli ustawimy referencjê na null
	public void unregister(YReceipt receipt) {
		mListeners.remove(receipt);
	}
}
