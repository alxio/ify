package pl.poznan.put.cs.ify.appify.receipts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YGPSFeature;
import pl.poznan.put.cs.ify.api.features.YGeocoderEvent;
import pl.poznan.put.cs.ify.api.features.YGeocoderFeature;
import pl.poznan.put.cs.ify.api.features.YSMSEvent;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YLocation;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.api.params.YPosition;

public class GeocoderReceipt extends YReceipt {

	private HashMap<String, Long> map = new HashMap<String, Long>();
	private ArrayList<String> mPending = new ArrayList<String>();

	@Override
	public long requestFeatures() {
		return Y.GPS | Y.Geocoder | Y.SMS;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("NUMER", YParamType.String, "+48792571392");
		//params.add("POSITION", YParamType.YPosition, new YPosition(0, 0, 100));
	}
	
	private void requestGeocoding() {
		YLocation loc = ((YGPSFeature) mFeatures.get(Y.GPS)).getLastLocation();
		YLog.d("lastLoc", loc + "");

		if (loc != null) {
			((YGeocoderFeature) mFeatures.get(Y.Geocoder)).requestAddress(
					loc.getLatitude(), loc.getLongitude());
		}
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Geocoder) {
			String addres = ((YGeocoderEvent) event).getAddress();
			YLog.d("GEOCODER", addres);
			YSMSFeature smsFeature = (YSMSFeature) mFeatures.get(Y.SMS);
			for (String rec : mPending) {
				smsFeature.sendSMS(rec, addres);
			}
			mPending.clear();
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

	@Override
	public String getName() {
		return "GeocoderReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new GeocoderReceipt();
	}

}
