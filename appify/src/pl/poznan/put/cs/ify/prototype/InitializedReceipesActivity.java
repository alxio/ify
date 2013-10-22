package pl.poznan.put.cs.ify.prototype;

import java.util.List;

import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.appify.R.layout;
import pl.poznan.put.cs.ify.appify.R.menu;
import pl.poznan.put.cs.ify.core.YReceiptInfo;
import pl.poznan.put.cs.ify.services.YReceiptsService;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;

public class InitializedReceipesActivity extends Activity {

	private View mEmptyIndicator;
	private View mLoadingLayout;

	private List<YReceiptInfo> mReceipts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initialized_receipes);

		initUI();
		showLoadingUI(true);
		getActiveReceipts();
	}

	private void getActiveReceipts() {
		BroadcastReceiver receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				unregisterReceiver(this);
				mReceipts = parseReceipts(intent);
				showLoadingUI(false);
			}

			private List<YReceiptInfo> parseReceipts(Intent intent) {
				Bundle infos = intent.getBundleExtra(YReceiptsService.RECEIPT_INFOS);
				return YReceiptInfo.listFromBundle(infos);
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(YReceiptsService.ACTION_GET_RECEIPTS_RESPONSE);
		registerReceiver(receiver, intentFilter);

		Intent activeReceiptsRequest = new Intent();
		activeReceiptsRequest.setAction(YReceiptsService.ACTION_GET_RECEIPTS_REQUEST);
		sendBroadcast(activeReceiptsRequest);
	}

	private void initUI() {
		mLoadingLayout = findViewById(R.id.loading_layout);
		mEmptyIndicator = findViewById(R.id.empty_indicator);
	}

	private void showLoadingUI(boolean visible) {
		mEmptyIndicator.setVisibility(View.GONE);
		if (visible) {
			mLoadingLayout.setVisibility(View.VISIBLE);
		} else {
			mLoadingLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.initialized_receipes, menu);
		return true;
	}

}
