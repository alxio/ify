package pl.poznan.put.cs.ify.api;

import java.util.HashSet;
import java.util.Set;

import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.Context;

public abstract class YFeature {
	protected Context mContext = null;

	public abstract int getId();

	public abstract String getName();

	public void initialize(Context ctx, IYReceiptHost srv) {
		mContext = ctx;
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

	private int mReceiptsCount;

	public void addReceipt(YReceipt receipt) {
		YLog.d("FEATURE", "RegisterReceipt: " + receipt.getName() + " to "
				+ getName());
		mReceiptsCount++;
		registerReceipt(receipt);
	}

	public boolean isUsed() {
		return mReceiptsCount > 0;
	}

	public void removeUser(YReceipt receipt) {
		YLog.d("FEATURE", "UnregisterReceipt: " + receipt.getName() + " from "
				+ getName());
		unregisterReceipt(receipt);
		mReceiptsCount--;
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
