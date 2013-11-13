package pl.poznan.put.cs.ify.api.group;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import pl.poznan.put.cs.ify.api.group.PoolingSolution.Callback;

public class PoolingSolution {

	private static final String ECHO = "http://ify.cs.put.poznan.pl/~scony/marketify/mock/echo.php";
	private static final String URL = "http://ify.cs.put.poznan.pl/~scony/marketify/mock/handler.php";
	private Context mContext;
	private Callback mCallback;
	private RequestQueue mRequestQueue;
	private ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.v("PULLING", "onErrorResponse " + error);
		}
	};
	private Listener<JSONObject> listener = new Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			Log.v("POOLING", "onResponse " + response);
			mCallback.onResponse(response);
		}
	};

	public PoolingSolution(PoolingSolution.Callback callback, Context context) {
		mCallback = callback;
		mRequestQueue = Volley.newRequestQueue(context);

	}

	public interface Callback {

		void onResponse(JSONObject response);

	}

	public void sendJson(String json) {
		Log.v("POOLING", json);
		try {
			JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL,
					new JSONObject(json), listener, errorListener);
			mRequestQueue.add(request);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
