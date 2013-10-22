package pl.poznan.put.cs.ify.api;

import java.util.HashSet;
import java.util.Set;

import pl.poznan.put.cs.ify.api.exceptions.UninitializedException;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public abstract class YFeature {
	/**
	 * 
	 * @return class name
	 */
	public abstract String getName();

	public abstract void initialize(Context ctx, YReceiptsService srv); // TODO:
																		// Remove
																		// first
																		// arg
																		// maybe

	public abstract void uninitialize();

	public void registerReceipt(YReceipt receipt) {
		mListeners.add(receipt);
	}

	public void unregisterReceipt(YReceipt receipt) {
		mListeners.remove(receipt);
	}

	private int mReceiptsCount;

	public void addReceipt(YReceipt receipt) {
		Log.d("FEATURE", "RegisterReceipt: " + receipt.getName() + " to "
				+ getName());
		mReceiptsCount++;
		registerReceipt(receipt);
	}

	public boolean isUsed() {
		return mReceiptsCount > 0;
	}

	public void removeUser(YReceipt receipt) {
		Log.d("FEATURE", "UnregisterReceipt: " + receipt.getName() + " from "
				+ getName());
		unregisterReceipt(receipt);
		mReceiptsCount--;
	}

	private Set<YReceipt> mListeners = new HashSet<YReceipt>();

	// public void register(YReceipt receipt) {
	// mListeners.add(receipt);
	// }

	public void sendNotification(Bundle data) throws UninitializedException {
		Set<YReceipt> toDelete = new HashSet<YReceipt>();
		for (YReceipt receipt : mListeners) {
			if (receipt != null)
				receipt.handleData(this, data);
			else
				toDelete.add(receipt);
		}
		mListeners.removeAll(toDelete);
	}

	public void trySendNotification(Bundle data) {
		try {
			sendNotification(data);
		} catch (UninitializedException e) {
			Log.d("trySendNotification", e.toString());
			// TODO: handle it, let user know someone created bad receipt.
		}
	}

	// // Wo³ane przy niejednorazowych triggerach po niszczeniu recepty, w
	// zasadzie
	// // mo¿na pomin¹æ, jeœli ustawimy referencjê na null
	// public void unregister(YReceipt receipt) {
	// mListeners.remove(receipt);
	// }

}
