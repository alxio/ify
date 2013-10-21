package pl.poznan.put.cs.ify.prototype;

import java.util.ArrayList;

import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.appify.receipts.WifiInJob;
import pl.poznan.put.cs.ify.appify.receipts.WifiOffWhenLowBattery;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ArrayList<YReceipt> mReceiptsList;
	private ListView mReceiptsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mReceiptsListView = (ListView) findViewById(R.id.receipts_list_view);
		mReceiptsList.add(new WifiInJob());
		mReceiptsList.add(new WifiOffWhenLowBattery());

		// TODO: Adapter
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void activateReceipt(YReceipt receipt) {
		YFeatureList feats = new YFeatureList();
		receipt.requestFeatures(feats);
		YParamList params = new YParamList();
		receipt.requestParams(params);
		// ...
	}
}
