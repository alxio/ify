package pl.poznan.put.cs.ify.app;

import java.util.List;

import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.ui.IOnParamsProvidedListener;
import pl.poznan.put.cs.ify.app.ui.OptionsDialog;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
				dialog.setOnParamsProvidedListener(new IOnParamsProvidedListener() {

					@Override
					public void onParamsProvided(YParamList requiredParams,
							YParamList optionalParams, String receipt) {
						Intent receiptIntent = new Intent(
								YReceiptsService.ACTION_ACTIVATE_RECEIPT);
						Log.d("INTENT", "enableReceipt: " + receipt
								+ "params: " + requiredParams);
						receiptIntent.putExtra(YReceiptsService.RECEIPT,
								receipt);
						receiptIntent.putExtra(YReceiptsService.PARAMS,
								requiredParams);
						// receiptIntent.putExtra("OPTIONAL", optionalParams);
						sendBroadcast(receiptIntent);
					}

				});
				ft.add(dialog, "RECEIPT_OPTIONS").commit();
			}

		});
		BroadcastReceiver availableReceiptsReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle b = intent
						.getBundleExtra(YReceiptsService.AVAILABLE_RECEIPTS);
				List<YReceiptInfo> listFromBundle = YReceiptInfo
						.listFromBundle(b);
				recipesAdapter.setData(listFromBundle);
				recipesAdapter.notifyDataSetChanged();
			}
		};
		IntentFilter filter = new IntentFilter(
				YReceiptsService.AVAILABLE_RESPONSE);
		registerReceiver(availableReceiptsReceiver, filter);

		Intent i = new Intent(YReceiptsService.AVAILABLE_REQUEST);
		sendBroadcast(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recipes_list, menu);
		return true;
	}

}
