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
package pl.poznan.put.cs.ify.api.group;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class PoolingSolution {

	// public static final String ECHO_URL =
	// "http://ify.cs.put.poznan.pl/~scony/marketify/mock/echo.php";
	// public static final String URL =
	// "http://ify.cs.put.poznan.pl/~scony/marketify/mock/handler.php";
	// public static final String NEW =
	// "http://ify.cs.put.poznan.pl/WebIFY-1.0/rest/recipe";
	// public static final String LOCAL =
	// "http://192.168.1.9:8080/WebIFY/rest/recipe";
	private String mUrl;
	private YComm mComm;
	private RequestQueue mRequestQueue;
	private Timer mTimer;

	private ErrorListener errorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Log.v("POOLING", "onErrorResponse " + error);
		}
	};
	private Listener<JSONObject> listener = new Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			Log.v("POOLING", "onResponse " + response);
			mComm.deliverEvent(new YGroupEvent(YCommData
					.fromJsonObject(response)));
		}
	};

	public PoolingSolution(YComm comm, Context context, long period, String url) {
		mUrl = url + "recipe";
		mComm = comm;
		mRequestQueue = QueueSingleton.getInstance(context);
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				pool();
			}
		}, 0, period);
	}

	public void sendJson(JSONObject json) {
		Log.v("POOLING", "URL" + json.toString() + "url " + mUrl);
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, mUrl,
				json, listener, errorListener) {
			@Override
			public HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json");
				return params;
			}
		};
		request.setShouldCache(false);
		mRequestQueue.add(request);
	}

	private void pool() {
		Log.d("POOLDEBUG", "pool() called");
		try {
			sendJson(mComm.getPoolRequest().toJsonObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void uninitialize() {
		mTimer.cancel();
	}

	public void setServerUrl(String url) {
		mUrl = url;
	}
}
