package pl.poznan.put.cs.ify.services;

import pl.poznan.put.cs.ify.core.YReceipt;
import android.app.IntentService;
import android.content.Intent;


//W sumie nie wiem, czy to si� przyda czy nie, chcia�em jaki� serwis wrzuci� i nie wiem co dalej.
public class ReceiptsService extends IntentService {
    public ReceiptsService(String name, YReceipt receipt) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	@Override
    protected void onHandleIntent(Intent workIntent) {
		// TODO Auto-generated constructor stub
    }
}