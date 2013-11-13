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

import pl.poznan.put.cs.ify.api.group.PullingSolution.Callback;

public class PullingSolution {

	private static final String URL = "http://ify.cs.put.poznan.pl/~scony/marketify/echo.php";
	private Context mContext;
	private Callback mCallback;
	private RequestQueue mRequestQueue;
	private ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.d("PULLING", "onErrorResponse " + error);
		}
	};
	private Listener<JSONObject> listener = new Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			Log.d("PULLING", "onResponse " + response);
			mCallback.onResponse(response);
		}
	};

	public PullingSolution(PullingSolution.Callback callback, Context context) {
		mCallback = callback;
		mRequestQueue = Volley.newRequestQueue(context);

	}

	public interface Callback {

		void onResponse(JSONObject response);

	}

	public void sendJson(String json) {
		Log.d("PULLING", json);
		try {
			JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL,
					new JSONObject(json), listener, errorListener);
			mRequestQueue.add(request);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
