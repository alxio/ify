package pl.poznan.put.cs.ify.app.market;

import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.app.market.FileRequest.onFileDeliveredListener;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.jars.JarBasement;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MarketInfoDetailsFrag extends DialogFragment {

	private MarketInfo mMarketInfo;
	private AlertDialog mLoadingDialog;

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
		return v;
	}

	private void initGui(View v, MarketInfo marketInfo) {
		TextView rate = (TextView) v.findViewById(R.id.tv_rate);
		TextView desc = (TextView) v.findViewById(R.id.tv_desc);
		TextView date = (TextView) v.findViewById(R.id.tv_date);
		TextView name = (TextView) v.findViewById(R.id.tv_name);

		rate.setText("TODO");
		desc.setMovementMethod(new ScrollingMovementMethod());
		desc.setText(marketInfo.getDescription());
		date.setText("TODO");
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
