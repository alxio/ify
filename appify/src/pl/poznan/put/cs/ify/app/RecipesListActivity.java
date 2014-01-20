package pl.poznan.put.cs.ify.app;

import java.util.List;

import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.market.MarketActivity;
import pl.poznan.put.cs.ify.app.ui.IOnParamsProvidedListener;
import pl.poznan.put.cs.ify.app.ui.OptionsDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecipesListActivity extends FragmentActivity {

	private RecipesAdapter recipesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_list);
		ListView recipesListView = (ListView) findViewById(R.id.list_recipes);
		recipesAdapter = new RecipesAdapter(this);
		recipesListView.setAdapter(recipesAdapter);
		recipesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				YRecipeInfo item = recipesAdapter.getItem(pos);
				initOptionsDialog(item.getRequiredParams(), item.getOptionalParams(), item.getName());
			}

			private void initOptionsDialog(YParamList required, YParamList optional, String name) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				OptionsDialog dialog = OptionsDialog.getInstance(required, optional, name, required.getFeatures());
				dialog.setOnParamsProvidedListener(new IOnParamsProvidedListener() {

					@Override
					public void onParamsProvided(YParamList requiredParams, YParamList optionalParams, String recipe) {
						Intent recipeIntent = new Intent(YRecipesService.ACTION_ACTIVATE_Recipe);
						Log.d("INTENT", "enableRecipe: " + recipe + "params: " + requiredParams);
						recipeIntent.putExtra(YRecipesService.Recipe, recipe);
						recipeIntent.putExtra(YRecipesService.PARAMS, requiredParams);
						// recipeIntent.putExtra("OPTIONAL", optionalParams);
						sendBroadcast(recipeIntent);
					}

				});
				ft.add(dialog, "Recipe_OPTIONS").commit();
			}

		});
		BroadcastReceiver availableRecipesReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle b = intent.getBundleExtra(YRecipesService.AVAILABLE_RecipeS);
//				List<YRecipeInfo> listFromBundle = YRecipeInfo.listFromBundle(b);
//				recipesAdapter.setData(listFromBundle);
//				recipesAdapter.notifyDataSetChanged();
			}
		};
		IntentFilter filter = new IntentFilter(YRecipesService.AVAILABLE_RESPONSE);
		registerReceiver(availableRecipesReceiver, filter);

		Intent i = new Intent(YRecipesService.AVAILABLE_REQUEST);
		sendBroadcast(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionActive:
			startActivity(new Intent(this, InitializedReceipesActivity.class));
			return true;
		case R.id.actionLogs:
			Intent recipeIntent = new Intent(YRecipesService.TOGGLE_LOG);
			sendBroadcast(recipeIntent);
			return true;
		case R.id.actionMarket:
			startActivity(new Intent(this, MarketActivity.class));
			return true;
		case R.id.actionAvaible:
			startActivity(new Intent(this, RecipesListActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
