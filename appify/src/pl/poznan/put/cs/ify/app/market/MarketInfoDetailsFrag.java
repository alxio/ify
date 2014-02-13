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
package pl.poznan.put.cs.ify.app.market;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.app.market.FileRequest.onFileDeliveredListener;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.jars.JarBasement;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public class MarketInfoDetailsFrag extends DialogFragment {

	private MarketInfo mMarketInfo;
	private AlertDialog mLoadingDialog;
	private static final DecimalFormat rateFormat = new DecimalFormat("0.00");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm dd-MM-yyyy", Locale.getDefault());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(0, R.style.AppTheme);
	}

	public static MarketInfoDetailsFrag getInstance(MarketInfo marketInfo) {
		MarketInfoDetailsFrag frag = new MarketInfoDetailsFrag();
		Bundle args = new Bundle();
		args.putParcelable("MARKET_INFO", marketInfo);
		frag.setArguments(args);
		return frag;
	}

	private MarketInfo getMarketInfo() {
		if (mMarketInfo == null) {
			mMarketInfo = getArguments().getParcelable("MARKET_INFO");
		}
		return mMarketInfo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.market_details, null);
		MarketInfo marketInfo = getMarketInfo();
		initGui(v, marketInfo);
		getDialog().setTitle("Recipe details");
		return v;
	}

	private void initGui(View v, MarketInfo marketInfo) {
		TextView rate = (TextView) v.findViewById(R.id.tv_rate);
		TextView desc = (TextView) v.findViewById(R.id.tv_desc);
		TextView date = (TextView) v.findViewById(R.id.tv_date);
		TextView name = (TextView) v.findViewById(R.id.tv_name);
		ListView commentsList = (ListView) v.findViewById(R.id.lv_comments);
		TextView commentsLabel = (TextView) v.findViewById(R.id.tv_comments);
		View commentsSep = v.findViewById(R.id.comments_sep);

		if (marketInfo.getComments() == null
				|| marketInfo.getComments().isEmpty()) {
			commentsList.setVisibility(View.GONE);
			commentsLabel.setVisibility(View.GONE);
			commentsSep.setVisibility(View.GONE);
		} else {
			CommentsAdapter commentsAdapter = new CommentsAdapter(
					marketInfo.getComments(), (LayoutInflater) getActivity()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
			commentsList.setAdapter(commentsAdapter);
		}

		try {
			rate.setText("Rate: " + rateFormat.format(marketInfo.getRate()));
		} catch (Exception ex) {
			rate.setText("Not yet rated");
		}

		desc.setMovementMethod(new ScrollingMovementMethod());
		desc.setText(marketInfo.getDescription());
		date.setText(dateFormat.format(new Date(marketInfo.getTimestamp())));
		name.setText(marketInfo.getName());

		Button download = (Button) v.findViewById(R.id.btn_download);
		download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showLoadingButton();
				downloadJar(getMarketInfo());
			}
		});
	}

	protected void showLoadingButton() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Downloading " + getMarketInfo().getName());
		builder.setMessage("In progress...");
		mLoadingDialog = builder.create();
		mLoadingDialog.show();
	}

	public void downloadJar(final MarketInfo info) {
		FileRequest jarRequest = new FileRequest(Method.GET, info.getUrl(),
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						mLoadingDialog.hide();
					}
				}, new onFileDeliveredListener() {

					@Override
					public void onResponseDelivered(byte[] response) {
						Log.d("BYTE SIZE", response.length + "");
						JarBasement jarBasement = new JarBasement(getActivity());
						jarBasement.putJar(response, info.getName());
						mLoadingDialog.hide();
					}
				});
		QueueSingleton.getInstance(getActivity()).add(jarRequest);
	}
}
