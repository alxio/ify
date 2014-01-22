package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class YInternetFeature extends YFeature {
	private RequestQueue mQueue;

	enum ResponseType {
		String,
	}

	private class Listener<T> implements com.android.volley.Response.Listener<T>, ErrorListener {
		private YRecipe mRecipe;
		private ResponseType mType;

		public Listener(YRecipe recipe, ResponseType type) {
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

	private void deliverResponse(ResponseType type, Object response, YRecipe recipe) {
		if (!mListeners.contains(recipe)) {
			return; // recipe is no longer active
		}
		recipe.tryHandleEvent(new YInternetEvent(response, type));
	}

	@Override
	public long getId() {
		return Y.Internet;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		mQueue = Volley.newRequestQueue(srv.getContext());
	}

	/**
	 * Downloads string from given URL with GET request.
	 */
	public void requestString(String url, YRecipe recipe) {
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
