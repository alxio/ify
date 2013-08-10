package pl.poznan.put.cs.ify.core;

import android.content.Context;

public interface YFeature {
	/**
	 * 
	 * @return class name
	 */
	public String getName(); 
	public void initialize(Context ctx);
}
