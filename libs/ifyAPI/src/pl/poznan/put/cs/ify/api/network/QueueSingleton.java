package pl.poznan.put.cs.ify.api.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class QueueSingleton {

	private static RequestQueue mInstance;

	public static RequestQueue getInstance(Context context) {
		if (mInstance == null) {
			mInstance = Volley.newRequestQueue(context);
		}
		mInstance.getCache().clear();
		return mInstance;
	}
}
