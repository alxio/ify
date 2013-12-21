package pl.poznan.put.cs.ify.app.fragments;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.app.ActiveReceipesAdapter;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.app.ui.InitializedReceiptDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActiveReceiptInfo;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class InitializedReceipesFragment extends Fragment {

	private View mEmptyIndicator;
	private View mLoadingLayout;

	private ActiveReceipesAdapter mAdapter;

	private List<ActiveReceiptInfo> mReceipts;
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_initialized_receipes, null);
		initUI(v);
		if (mReceipts == null) {
			showLoadingUI(true);
		} else {
			mAdapter = new ActiveReceipesAdapter(getActivity(), mReceipts);
			mListView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			int dataSize = mAdapter.getCount();
			Log.d("ooo", dataSize + "");
			showLoadingUI(false);
		}
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainActivity) getActivity()).requestActiveRecipesList();
	}

	private void getActiveReceipts() {
		// TODO: CHANGE TO MESSANGER
		// Log.d("LIFECYCLE", "getActiveReceipts");
		// BroadcastReceiver receiver = new BroadcastReceiver() {
		//
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// // unregisterReceiver(this);
		// mReceipts = parseReceipts(intent);
		// showLoadingUI(false);
		// if (mReceipts == null) {
		// mReceipts = new ArrayList<ActiveReceiptInfo>();
		// }
		// mAdapter = new
		// ActiveReceipesAdapter(InitializedReceipesActivity.this, mReceipts);
		// mListView.setAdapter(mAdapter);
		// mAdapter.notifyDataSetChanged();
		// int dataSize = mAdapter.getCount();
		// Log.d("ooo", dataSize + "");
		// }
		//
		// private List<ActiveReceiptInfo> parseReceipts(Intent intent) {
		// return
		// intent.getParcelableArrayListExtra(YReceiptsService.RECEIPT_INFOS);
		// }
		// };
		// IntentFilter intentFilter = new IntentFilter();
		// intentFilter.addAction(YReceiptsService.ACTION_GET_RECEIPTS_RESPONSE);
		// registerReceiver(receiver, intentFilter);
		//
		// Intent activeReceiptsRequest = new Intent();
		// activeReceiptsRequest.setAction(YReceiptsService.ACTION_GET_RECEIPTS_REQUEST);
		// sendBroadcast(activeReceiptsRequest);
	}

	private void initUI(View v) {
		mLoadingLayout = v.findViewById(R.id.loading_layout);
		mEmptyIndicator = v.findViewById(R.id.empty_indicator);
		mListView = (ListView) v.findViewById(R.id.active_receipes_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				// TODO: Calls logs
				// ActiveReceiptInfo item = mAdapter.getItem(pos);
				// Intent i = new Intent(YReceiptsService.ACTION_RECEIPT_LOGS);
				// i.putExtra(YReceiptsService.RECEIPT_TAG, item.getTag());
				// sendBroadcast(i);

				ActiveReceiptInfo item = mAdapter.getItem(pos);
				showActiveReceiptDialog(item);
			}
		});
	}

	private void showActiveReceiptDialog(ActiveReceiptInfo item) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		InitializedReceiptDialog dialog = InitializedReceiptDialog.getInstance(item);
		dialog.setCommInterface(new InitializedReceiptDialog.CommInterface() {

			@Override
			public void onDisableReceipt(int id) {
				Intent i = new Intent(YReceiptsService.ACTION_DEACTIVATE_RECEIPT);
				i.putExtra(YReceiptsService.RECEIPT_ID, id);
				((MainActivity) getActivity()).disableRecipe(id);
				showLoadingUI(true);

			}
		});
		ft.add(dialog, "RECEIPT_OPTIONS").commit();
	}

	private void showLoadingUI(boolean visible) {
		mEmptyIndicator.setVisibility(View.GONE);
		if (visible) {
			mLoadingLayout.setVisibility(View.VISIBLE);
		} else {
			mLoadingLayout.setVisibility(View.GONE);
		}
	}

	public void updateData(ArrayList<ActiveReceiptInfo> activeReceiptInfos) {
		// unregisterReceiver(this);
		mReceipts = activeReceiptInfos;
		if (getView() != null) {
			showLoadingUI(false);
			if (mReceipts == null) {
				mReceipts = new ArrayList<ActiveReceiptInfo>();
			}
			mAdapter = new ActiveReceipesAdapter(getActivity(), mReceipts);
			mListView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			int dataSize = mAdapter.getCount();
			Log.d("ooo", dataSize + "");
		}
	}
}
