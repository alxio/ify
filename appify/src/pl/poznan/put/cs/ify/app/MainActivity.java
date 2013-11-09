package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;

import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends YActivity {
	private ArrayList<YReceipt> mReceiptsList;
	private ListView mReceiptsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mReceiptsListView = (ListView) findViewById(R.id.receipts_list_view);
	}
	
	private void activateReceipt(YReceipt receipt) {
		YFeatureList feats = new YFeatureList();
		receipt.requestFeatures(feats);
		YParamList params = new YParamList();
		receipt.requestParams(params);
		// ...
	}
}
