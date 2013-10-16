package pl.poznan.put.cs.ify.prototype;

import pl.poznan.put.cs.ify.android.ui.OptionsDialog;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YReceiptInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecipesListActivity extends FragmentActivity {

	private AvailableRecipesManager mAvailableRecipesManager;
	private InitializedRecipesManager mInitializedRecipesManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_list);
		mAvailableRecipesManager = new AvailableRecipesManager();
		mInitializedRecipesManager = new InitializedRecipesManager();
		ListView recipesListView = (ListView) findViewById(R.id.list_recipes);
		final RecipesAdapter recipesAdapter = new RecipesAdapter(this,
				mInitializedRecipesManager, mAvailableRecipesManager);
		recipesListView.setAdapter(recipesAdapter);
		recipesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				YReceiptInfo item = recipesAdapter.getItem(pos);
				initOptionsDialog(item.getRequiredParams(),
						item.getOptionalParams(), item.getName());

			}

			private void initOptionsDialog(YParamList required,
					YParamList optional, String name) {
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				OptionsDialog dialog = OptionsDialog.getInstance(required,
						optional, name);
				ft.add(dialog, "RECEIPT_OPTIONS").commit();
			}
		});

		// // tests
		// // Initialize the songs array
		// // String[] songsArray = new String[10];
		//
		// // Fill the songs array by using a for loop
		// // for (int i = 0; i < songsArray.length; i++) {
		// // songsArray[i] = "Song " + i;
		// // }
		//
		// // For this moment, you have list of songs and a ListView where you
		// can
		// // display a list.
		// // But how can we put this data set to the list?
		// // This is where you need an Adapter
		//
		// // context - The current context.
		// // resource - The resource ID for a layout file containing a layout
		// to
		// // use when instantiating views.
		// // textViewResourceId - The id of the TextView within the layout
		// // resource to be populated
		// // From the third parameter, you plugged the data set to adapter
		// ArrayAdapter arrayAdapter = new ArrayAdapter(this,
		// // android.R.layout.simple_list_item_1, songsArray);
		// //
		// // // By using setAdapter method, you plugged the ListView with
		// adapter
		// recipesListView.setAdapter(arrayAdapter);
		// recipesListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// Toast.makeText(RecipesListActivity.this, arg2 + "",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recipes_list, menu);
		return true;
	}

}
