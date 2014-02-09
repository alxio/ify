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
package pl.poznan.put.cs.ify.app.fragments;

import java.util.List;

import org.json.JSONArray;

import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.app.market.JsonParser;
import pl.poznan.put.cs.ify.app.market.MarketInfo;
import pl.poznan.put.cs.ify.app.market.MarketInfoAdapter;
import pl.poznan.put.cs.ify.app.market.MarketInfoDetailsFrag;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class MarketFragment extends Fragment {

	private static final int LIMIT = 10;

	private MarketInfoAdapter mAdapter;
	private ListView mRecipesList;
	private View mLoadingView;
	private View mErrorView;

	private View mFooter;
	private int mCurrentPage = 1;

	private RequestQueue mRequestQueue;

	private View mLoadMore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_market, null);
		// TODO: Keep this request queue in singleton
		mRequestQueue = QueueSingleton.getInstance(getActivity());
		mAdapter = new MarketInfoAdapter(getActivity());
		initGui(v);
		loadData();
		return v;
	}

	private void initGui(View v) {
		mLoadMore = v.findViewById(R.id.load_more_layout);
		mLoadMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadData();
			}
		});
		mRecipesList = (ListView) v.findViewById(R.id.market_list);
		mLoadingView = v.findViewById(R.id.loading_layout);
		mErrorView = v.findViewById(R.id.error_layout);
		mRecipesList.setAdapter(mAdapter);
		mRecipesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
				MarketInfo item = mAdapter.getItem(pos);
				// downloadJar(item);
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.add(MarketInfoDetailsFrag.getInstance(item), "MARKET_DETAILS");
				ft.commit();
			}
		});
		mErrorView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mErrorView.setVisibility(View.GONE);
				loadData();
			}
		});
	}

	private void loadData() {
		mLoadingView.setVisibility(View.VISIBLE);
		mLoadMore.setVisibility(View.GONE);
		JsonArrayRequest arrayReq = new JsonArrayRequest(getUrl(), new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				mLoadingView.setVisibility(View.GONE);
				JsonParser parser = new JsonParser();
				Log.d("RESPONSE", response.toString());
				List<MarketInfo> recipeInfos = parser.parseRecipesInfo(response);
				mAdapter.addData(recipeInfos);
				Log.d("RESPONSE SIZE", mAdapter.getCount() + "");
				mAdapter.notifyDataSetChanged();
				if (recipeInfos.size() < LIMIT) {
					mLoadMore.setVisibility(View.GONE);
				} else {
					mLoadMore.setVisibility(View.VISIBLE);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				mLoadingView.setVisibility(View.GONE);
				mErrorView.setVisibility(View.VISIBLE);
				mLoadMore.setVisibility(View.VISIBLE);
			}
		});
		arrayReq.setShouldCache(false);
		mRequestQueue.add(arrayReq);
	}

	private String getUrl() {
		int page = mAdapter.getCount() / LIMIT + 1;
		return "http://ify.cs.put.poznan.pl/~scony/marketify/api/recipes.php?page=" + page + "&limit=" + LIMIT;
	}

}
