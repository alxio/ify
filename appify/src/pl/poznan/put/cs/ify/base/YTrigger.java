package pl.poznan.put.cs.ify.base;

import java.util.HashSet;
import java.util.Set;

import pl.poznan.put.cs.ify.api.UninitializedException;

public class YTrigger {
	private Set<YReceipt>mListeners = new HashSet<YReceipt>();
	public void register(YReceipt receipt){
		mListeners.add(receipt);
	}
	public void activate() throws UninitializedException{
		Set<YReceipt>toDelete = new HashSet<YReceipt>();
		for(YReceipt receipt : mListeners){
			if(receipt != null)
				receipt.handleTrigger(this);
			else
				toDelete.add(receipt);
		}
		mListeners.removeAll(toDelete);
	}
	
	//Wo³ane przy niejednorazowych triggerach po niszczeniu recepty, w zasadzie mo¿na pomin¹æ, jeœli ustawimy referencjê na null
	public void unregister(YReceipt receipt){
		mListeners.remove(receipt);
	}
}
