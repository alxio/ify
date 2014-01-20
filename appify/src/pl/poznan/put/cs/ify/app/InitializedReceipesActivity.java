package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.app.ui.InitializedRecipeDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActiveRecipeInfo;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class InitializedReceipesActivity extends YActivity {

	private View mEmptyIndicator;
	private View mLoadingLayout;

	private ActiveReceipesAdapter mAdapter;

	private List<ActiveRecipeInfo> mRecipes;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initialized_receipes);
		initUI();
		showLoadingUI(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getActiveRecipes();
	}

	private void getActiveRecipes() {
		Log.d("LIFECYCLE", "getActiveRecipes");
		BroadcastReceiver receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// unregisterReceiver(this);
				mRecipes = parseRecipes(intent);
				showLoadingUI(false);
				if (mRecipes == null) {
					mRecipes = new ArrayList<ActiveRecipeInfo>();
				}
				mAdapter = new ActiveReceipesAdapter(InitializedReceipesActivity.this, mRecipes);
				mListView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				int dataSize = mAdapter.getCount();
				Log.d("ooo", dataSize + "");
			}

			private List<ActiveRecipeInfo> parseRecipes(Intent intent) {
				return intent.getParcelableArrayListExtra(YRecipesService.Recipe_INFOS);
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(YRecipesService.ACTION_GET_RecipeS_RESPONSE);
		registerReceiver(receiver, intentFilter);

		Intent activeRecipesRequest = new Intent();
		activeRecipesRequest.setAction(YRecipesService.ACTION_GET_RecipeS_REQUEST);
		sendBroadcast(activeRecipesRequest);
	}

	private void initUI() {
		mLoadingLayout = findViewById(R.id.loading_layout);
		mEmptyIndicator = findViewById(R.id.empty_indicator);
		mListView = (ListView) findViewById(R.id.active_receipes_list);
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
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		InitializedRecipeDialog dialog = InitializedRecipeDialog.getInstance(item);
		dialog.setCommInterface(new InitializedRecipeDialog.CommInterface() {

			@Override
			public void onDisableRecipe(int id) {
				Intent i = new Intent(YRecipesService.ACTION_DEACTIVATE_Recipe);
				i.putExtra(YRecipesService.Recipe_ID, id);
				sendBroadcast(i);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
