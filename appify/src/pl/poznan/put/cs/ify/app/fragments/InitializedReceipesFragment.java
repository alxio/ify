package pl.poznan.put.cs.ify.app.fragments;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.app.ActiveReceipesAdapter;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.app.ui.InitializedRecipeDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActiveRecipeInfo;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class InitializedReceipesFragment extends Fragment {

	private View mEmptyIndicator;
	private View mLoadingLayout;

	private ActiveReceipesAdapter mAdapter;

	private List<ActiveRecipeInfo> mRecipes;
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_initialized_receipes, null);
		initUI(v);
		if (mRecipes == null) {
			showLoadingUI(true);
		} else {
			mAdapter = new ActiveReceipesAdapter(getActivity(), mRecipes);
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

	private void getActiveRecipes() {
		// TODO: CHANGE TO MESSANGER
		// Log.d("LIFECYCLE", "getActiveRecipes");
		// BroadcastReceiver receiver = new BroadcastReceiver() {
		//
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// // unregisterReceiver(this);
		// mRecipes = parseRecipes(intent);
		// showLoadingUI(false);
		// if (mRecipes == null) {
		// mRecipes = new ArrayList<ActiveRecipeInfo>();
		// }
		// mAdapter = new
		// ActiveReceipesAdapter(InitializedReceipesActivity.this, mRecipes);
		// mListView.setAdapter(mAdapter);
		// mAdapter.notifyDataSetChanged();
		// int dataSize = mAdapter.getCount();
		// Log.d("ooo", dataSize + "");
		// }
		//
		// private List<ActiveRecipeInfo> parseRecipes(Intent intent) {
		// return
		// intent.getParcelableArrayListExtra(YRecipesService.Recipe_INFOS);
		// }
		// };
		// IntentFilter intentFilter = new IntentFilter();
		// intentFilter.addAction(YRecipesService.ACTION_GET_RecipeS_RESPONSE);
		// registerReceiver(receiver, intentFilter);
		//
		// Intent activeRecipesRequest = new Intent();
		// activeRecipesRequest.setAction(YRecipesService.ACTION_GET_RecipeS_REQUEST);
		// sendBroadcast(activeRecipesRequest);
	}

	private void initUI(View v) {
		mLoadingLayout = v.findViewById(R.id.loading_layout);
		mEmptyIndicator = v.findViewById(R.id.empty_indicator);
		mListView = (ListView) v.findViewById(R.id.active_receipes_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				// TODO: Calls logs
				// ActiveRecipeInfo item = mAdapter.getItem(pos);
				// Intent i = new Intent(YRecipesService.ACTION_Recipe_LOGS);
				// i.putExtra(YRecipesService.Recipe_TAG, item.getTag());
				// sendBroadcast(i);

				ActiveRecipeInfo item = mAdapter.getItem(pos);
				showActiveRecipeDialog(item);
			}
		});
	}

	private void showActiveRecipeDialog(ActiveRecipeInfo item) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		InitializedRecipeDialog dialog = InitializedRecipeDialog.getInstance(item);
		dialog.setCommInterface(new InitializedRecipeDialog.CommInterface() {

			@Override
			public void onDisableRecipe(int id) {
				Intent i = new Intent(YRecipesService.ACTION_DEACTIVATE_Recipe);
				i.putExtra(YRecipesService.Recipe_ID, id);
				((MainActivity) getActivity()).disableRecipe(id);
				showLoadingUI(true);

			}
		});
		ft.add(dialog, "Recipe_OPTIONS").commit();
	}

	private void showLoadingUI(boolean visible) {
		mEmptyIndicator.setVisibility(View.GONE);
		if (visible) {
			mLoadingLayout.setVisibility(View.VISIBLE);
		} else {
			mLoadingLayout.setVisibility(View.GONE);
		}
	}

	public void updateData(ArrayList<ActiveRecipeInfo> activeRecipeInfos) {
		// unregisterReceiver(this);
		mRecipes = activeRecipeInfos;
		if (getView() != null) {
			showLoadingUI(false);
			if (mRecipes == null) {
				mRecipes = new ArrayList<ActiveRecipeInfo>();
			}
			mAdapter = new ActiveReceipesAdapter(getActivity(), mRecipes);
			mListView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			int dataSize = mAdapter.getCount();
			Log.d("ooo", dataSize + "");
		}
	}
}
