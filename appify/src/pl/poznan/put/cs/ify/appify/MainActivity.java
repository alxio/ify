package pl.poznan.put.cs.ify.appify;

import java.util.ArrayList;

import pl.poznan.put.cs.ify.appify.receipts.WifiInJob;
import pl.poznan.put.cs.ify.appify.receipts.WifiOffWhenLowBattery;
import pl.poznan.put.cs.ify.core.YFeatureList;
import pl.poznan.put.cs.ify.core.YReceipt;
import pl.poznan.put.cs.ify.params.YParamList;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ArrayList<YReceipt> mReceiptsList;
	private ListView mReceiptsListView = (ListView)findViewById(R.id.receiptsListView);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mReceiptsList.add(new WifiInJob());
		mReceiptsList.add(new WifiOffWhenLowBattery());
		
		//TODO: Adapter
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
		//...
	}
}
