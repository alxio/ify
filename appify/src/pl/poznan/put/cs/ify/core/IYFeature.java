package pl.poznan.put.cs.ify.core;

import android.content.Context;

public interface IYFeature {
	/**
	 * 
	 * @return class name
	 */
	public String getName(); 
	public void initialize(Context ctx);
}
