package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.app.market.MarketActivity;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class YActivity extends FragmentActivity {
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
		case R.id.actionLogin:
			startActivity(new Intent(this, LoginActivity.class));
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
