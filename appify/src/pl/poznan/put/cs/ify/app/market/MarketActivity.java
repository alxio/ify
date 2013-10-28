package pl.poznan.put.cs.ify.app.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import pl.poznan.put.cs.ify.appify.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MarketActivity extends Activity {

	private static final String MARKET_URL = "http://ify.cs.put.poznan.pl/~scony/marketify/api/new.php";

	private MarketInfoAdapter mAdapter;
	private ListView mReceiptsList;
	private View mLoadingView;
	private View mErrorView;

	private RequestQueue mRequestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
		mRequestQueue = Volley.newRequestQueue(this);
		mAdapter = new MarketInfoAdapter(this);
		initGui();
		loadData();
	}

	private void initGui() {
		mReceiptsList = (ListView) findViewById(R.id.market_list);
		mLoadingView = findViewById(R.id.loading_layout);
		mErrorView = findViewById(R.id.error_layout);
		mReceiptsList.setAdapter(mAdapter);
	}

	public void reload(View v) {
		mErrorView.setVisibility(View.GONE);
		loadData();
	}

	private void loadData() {
		mLoadingView.setVisibility(View.VISIBLE);

		JsonArrayRequest arrayReq = new JsonArrayRequest(MARKET_URL,
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						mLoadingView.setVisibility(View.GONE);
						JsonParser parser = new JsonParser();
						Log.d("RESPONSE", response.toString());
						List<MarketInfo> receiptInfos = parser
								.parseReceiptsInfo(response);
						mAdapter.addData(receiptInfos);
						Log.d("RESPONSE SIZE", mAdapter.getCount() + "");
						mAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						mLoadingView.setVisibility(View.GONE);
						mErrorView.setVisibility(View.VISIBLE);
					}
				});
		mRequestQueue.add(arrayReq);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.market, menu);
		return true;
	}

}
