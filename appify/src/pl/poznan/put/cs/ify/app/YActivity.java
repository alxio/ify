package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.app.market.MarketActivity;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class YActivity extends Activity{
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.actionActive:
	        	startActivity(new Intent(this, InitializedReceipesActivity.class));
	            return true;
	        case R.id.actionLogs:
	    		Intent receiptIntent = new Intent(YReceiptsService.TOGGLE_LOG);
	    		sendBroadcast(receiptIntent);
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}
