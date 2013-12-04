package pl.poznan.put.cs.ify.api.group;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class PoolingSolution {

	public static final String ECHO_URL = "http://ify.cs.put.poznan.pl/~scony/marketify/mock/echo.php";
	public static final String URL = "http://ify.cs.put.poznan.pl/~scony/marketify/mock/handler.php";

	private YComm mComm;
	private RequestQueue mRequestQueue;
	private Timer mTimer;

	private ErrorListener errorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			// Log.v("POOLING", "onErrorResponse " + error);
		}
	};
	private Listener<JSONObject> listener = new Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			Log.v("POOLING", "onResponse " + response);
			mComm.deliverEvent(new YGroupEvent(YCommData.fromJsonObject(response)));
		}
	};

	public PoolingSolution(YComm comm, Context context, long period) {
		mComm = comm;
		mRequestQueue = Volley.newRequestQueue(context);
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				pool();
			}
		}, 0, period);
	}

	public void sendJson(JSONObject json) {
		Log.v("POOLING", json.toString());
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, URL, json, listener, errorListener);
		mRequestQueue.add(request);
	}

	private void pool() {
		try {
			Log.v("POOLING", "query:" + mComm.getPoolRequest().toJsonObject().toString());
			sendJson(mComm.getPoolRequest().toJsonObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void uninitialize() {
		mTimer.cancel();
	}
}
