package pl.poznan.put.cs.ify.api.features;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;

public class YGeocoderFeature extends YFeature {
	private RequestQueue mRequestQueue;
	private Listener<JSONObject> listener = new Listener<JSONObject>() {

		/**
		 * @param response
		 */
		@Override
		public void onResponse(JSONObject response) {
			try {
				JSONArray jsonArray = response.getJSONArray("results");
				if (jsonArray.length() > 0) {
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					String address = jsonObject.getString("formatted_address");
					sendNotification(new YGeocoderEvent(address));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	private ErrorListener errorListener;

	@Override
	public int getId() {
		return Y.Geocoder;
	}

	public void requestAddress(double lat, double lng) {
		String url = "http://maps.googleapis.com/maps/api/geocode/json?sensor=true&latlng=" + lat + "," + lng;
		JsonObjectRequest request = new JsonObjectRequest(url, null, listener, errorListener);
		mRequestQueue.add(request);
	}

	@Override
	protected void init(IYReceiptHost srv) {
		mRequestQueue = Volley.newRequestQueue(srv.getContext());
	}

	@Override
	public void uninitialize() {
	}

}
