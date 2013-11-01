package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.app.market.MarketActivity;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Well, its bad, but only temporary.
 * 
 */
public class MenuActivity extends YActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	public void onActiveClicked(View v) {
		startActivity(new Intent(this, InitializedReceipesActivity.class));
	}

	public void onAvailableClicked(View v) {
		startActivity(new Intent(this, RecipesListActivity.class));
	}

	public void onMarketClicked(View v) {
		startActivity(new Intent(this, MarketActivity.class));
	}

	public void onLogViewClicked(View v) {
		Intent receiptIntent = new Intent(YReceiptsService.TOGGLE_LOG);
		sendBroadcast(receiptIntent);
	}
}
