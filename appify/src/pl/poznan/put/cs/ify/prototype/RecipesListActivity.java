package pl.poznan.put.cs.ify.prototype;

import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.appify.R.layout;
import pl.poznan.put.cs.ify.appify.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecipesListActivity extends Activity {
	
	private AvailableRecipesManager mAvailableRecipesManager;
	private InitializedRecipesManager mInitializedRecipesManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_list);
		mAvailableRecipesManager = new AvailableRecipesManager();
		mInitializedRecipesManager = new InitializedRecipesManager();
		ListView recipesListView = (ListView) findViewById(R.id.list_recipes);
		RecipesAdapter recipesAdapter = new RecipesAdapter(this, mInitializedRecipesManager, mAvailableRecipesManager);
		recipesListView.setAdapter(recipesAdapter);
		recipesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recipes_list, menu);
		return true;
	}

}
