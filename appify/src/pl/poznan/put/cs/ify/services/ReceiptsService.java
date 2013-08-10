package pl.poznan.put.cs.ify.services;

import pl.poznan.put.cs.ify.YReceipt;
import android.app.IntentService;
import android.content.Intent;


//W sumie nie wiem, czy to siê przyda czy nie, chcia³em jakiœ serwis wrzuciæ i nie wiem co dalej.
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