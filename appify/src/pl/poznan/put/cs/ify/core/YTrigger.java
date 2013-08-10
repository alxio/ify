package pl.poznan.put.cs.ify.core;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.widget.Toast;

public abstract class YTrigger implements YFeature {
	protected Context mContext;
	private Set<YReceipt> mListeners = new HashSet<YReceipt>();

	@Override
	public final void initialize(Context ctx) {
		mContext = ctx;
		initialize();
	}

	protected abstract void initialize();

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
			Toast.makeText(mContext, "Module uninitialized: " + e,
					Toast.LENGTH_LONG).show(); // TODO: remove it!
		}
	}

	// Wo³ane przy niejednorazowych triggerach po niszczeniu recepty, w zasadzie
	// mo¿na pomin¹æ, jeœli ustawimy referencjê na null
	public void unregister(YReceipt receipt) {
		mListeners.remove(receipt);
	}
}
