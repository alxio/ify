package pl.poznan.put.cs.ify.appify.receipts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.content.IntentSender.SendIntentException;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YGPSEvent;
import pl.poznan.put.cs.ify.api.features.YGPSFeature;
import pl.poznan.put.cs.ify.api.features.YGeocoderEvent;
import pl.poznan.put.cs.ify.api.features.YGeocoderFeature;
import pl.poznan.put.cs.ify.api.features.YSMSEvent;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YLocation;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class GeocoderReceipt extends YReceipt {

	private HashMap<String, Long> map = new HashMap<String, Long>();
	private ArrayList<String> mPending = new ArrayList<String>();

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YGPSFeature());
		features.add(new YGeocoderFeature());
		features.add(new YSMSFeature());
	}

	@Override
	public void requestParams(YParamList params) {

	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.GEOCODER) {
			String addres = ((YGeocoderEvent) event).getAddress();
			YLog.d("GEOCODER", addres);
			YSMSFeature smsFeature = (YSMSFeature) mFeatures.get(Y.SMS);
			for (String rec : mPending) {
				smsFeature.sendSMS(rec, addres);
			}
			mPending.clear();
		} else if (event.getId() == Y.GPS) {

		} else if (event.getId() == Y.SMS) {

			YSMSEvent smsEvent = (YSMSEvent) event;
			String sender = smsEvent.getSender();
			YLog.d("SMS", smsEvent.getMessage().toString());
			if (smsEvent.getMessage().equals("Gdzie?")) {
				long time = Calendar.getInstance().getTimeInMillis();
				Long lastTime = map.get(sender);
				YLog.d("lastTime", lastTime + "");
				if (lastTime == null) {
					mPending.add(sender);
					requestGeocoding();
					map.put(sender, time);
				}
				if (lastTime != null) {
					YLog.d("time - lastTime", (time - lastTime) + "");

					if (time - lastTime > 30 * 60 * 1000) {
						mPending.add(sender);
						requestGeocoding();
					}
				}
			}
		}
	}

	private void requestGeocoding() {
		YLocation loc = ((YGPSFeature) mFeatures.get(Y.GPS)).getLastLocation();
		YLog.d("lastLoc", loc + "");

		if (loc != null) {
			((YGeocoderFeature) mFeatures.get(Y.GEOCODER)).requestAddress(
					loc.getLatitude(), loc.getLongitude());
		}
	}

	@Override
	public String getName() {
		return "GeocoderReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new GeocoderReceipt();
	}

}
