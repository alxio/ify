package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YReceipt;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class YNetFeature extends YFeature {
	private RequestQueue mQueue;

	enum ResponseType {
		String,
	}

	private class Listener<T> implements com.android.volley.Response.Listener<T>, ErrorListener {
		private YReceipt mRecipe;
		private ResponseType mType;

		public Listener(YReceipt recipe, ResponseType type) {
			mRecipe = recipe;
			mType = type;
		}

		@Override
		public void onResponse(T response) {
			deliverResponse(mType, response, mRecipe);
		}

		@Override
		public void onErrorResponse(VolleyError error) {
		}
	}

	private void deliverResponse(ResponseType type, Object response, YReceipt recipe) {
		if (!mListeners.contains(recipe)) {
			return; // recipe is no longer active
		}
		recipe.handleEvent(new YNetEvent(response, type));
	}

	@Override
	public long getId() {
		return Y.Internet;
	}

	@Override
	protected void init(IYReceiptHost srv) {
		mQueue = Volley.newRequestQueue(srv.getContext());
	}

	/**
	 * Downloads string from given URL with GET request.
	 */
	public void requestString(String url, YReceipt recipe) {
		Listener<String> listener = new Listener<String>(recipe, ResponseType.String);
		StringRequest req = new StringRequest(url, listener, listener);
		mQueue.add(req);
	}

	@Override
	public void uninitialize() {
		mQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
	}
}
