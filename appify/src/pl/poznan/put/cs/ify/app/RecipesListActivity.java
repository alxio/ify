package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.ui.IOnParamsProvidedListener;
import pl.poznan.put.cs.ify.app.ui.OptionsDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.AvailableRecipesManager;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
		mAvailableRecipesManager = new AvailableRecipesManager(this);
		mInitializedRecipesManager = new InitializedRecipesManager();
		ListView recipesListView = (ListView) findViewById(R.id.list_recipes);
		final RecipesAdapter recipesAdapter = new RecipesAdapter(this, mInitializedRecipesManager,
				mAvailableRecipesManager);
		recipesListView.setAdapter(recipesAdapter);
		recipesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				YReceiptInfo item = recipesAdapter.getItem(pos);
				initOptionsDialog(item.getRequiredParams(), item.getOptionalParams(), item.getName());

			}

			private void initOptionsDialog(YParamList required, YParamList optional, String name) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				OptionsDialog dialog = OptionsDialog.getInstance(required, optional, name);
				dialog.setOnParamsProvidedListener(new IOnParamsProvidedListener() {

					@Override
					public void onParamsProvided(YParamList requiredParams, YParamList optionalParams, String receipt) {
						Intent receiptIntent = new Intent(YReceiptsService.INTENT);
						Log.d("INTENT","enableReceipt: "+receipt+"params: "+requiredParams);
						receiptIntent.putExtra(YReceiptsService.RECEIPT, receipt);
						receiptIntent.putExtra(YReceiptsService.PARAMS, requiredParams);
						//receiptIntent.putExtra("OPTIONAL", optionalParams);
						sendBroadcast(receiptIntent);
					}

				});
				ft.add(dialog, "RECEIPT_OPTIONS").commit();
			}

		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recipes_list, menu);
		return true;
	}

}
