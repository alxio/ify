/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
		mQueue = QueueSingleton.getInstance(srv.getContext());
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
