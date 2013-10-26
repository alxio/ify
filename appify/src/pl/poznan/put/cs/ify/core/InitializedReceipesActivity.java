package pl.poznan.put.cs.ify.core;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.app.ActiveReceipesAdapter;
import pl.poznan.put.cs.ify.appify.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class InitializedReceipesActivity extends Activity {

	private View mEmptyIndicator;
	private View mLoadingLayout;

	private ActiveReceipesAdapter mAdapter;

	private List<ActiveReceiptInfo> mReceipts;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		startService(new Intent(this, YReceiptsService.class));
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
//				unregisterReceiver(this);
				mReceipts = parseReceipts(intent);
				showLoadingUI(false);
				if (mReceipts == null) {
					mReceipts = new ArrayList<ActiveReceiptInfo>();
				}
				mAdapter = new ActiveReceipesAdapter(
						InitializedReceipesActivity.this, mReceipts);
				mListView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				int dataSize = mAdapter.getCount();
				YLog.d("ooo", dataSize + "");
			}

			private List<ActiveReceiptInfo> parseReceipts(Intent intent) {
				return intent
						.getParcelableArrayListExtra(YReceiptsService.RECEIPT_INFOS);
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(YReceiptsService.ACTION_GET_RECEIPTS_RESPONSE);
		registerReceiver(receiver, intentFilter);

		Intent activeReceiptsRequest = new Intent();
		activeReceiptsRequest
				.setAction(YReceiptsService.ACTION_GET_RECEIPTS_REQUEST);
		sendBroadcast(activeReceiptsRequest);
	}

	private void initUI() {
		mLoadingLayout = findViewById(R.id.loading_layout);
		mEmptyIndicator = findViewById(R.id.empty_indicator);
		mListView = (ListView) findViewById(R.id.active_receipes_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				ActiveReceiptInfo item = mAdapter.getItem(pos);
				int id = item.getId();
				Intent i = new Intent(
						YReceiptsService.ACTION_DEACTIVATE_RECEIPT);
				i.putExtra(YReceiptsService.RECEIPT_ID, id);
				showLoadingUI(true);
				sendBroadcast(i);
			}
		});
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
