package pl.poznan.put.cs.ify.features;

import android.content.Context;

public interface YFeature {
	public abstract String getName();
	public abstract void initialize(Context ctx);
}
