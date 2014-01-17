package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YCallsFeature;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.features.events.YCallsEvent;
import pl.poznan.put.cs.ify.api.features.events.YSMSEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YSMSReceipt extends YReceipt {

	@Override
	public long requestFeatures() {
		return Y.SMS | Y.Calls;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("FROM", YParamType.Number, "+48606932226");
		params.add("TEXT", YParamType.String, "Nie moge teraz rozmawiac");

	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Calls) {
			YCallsEvent callsEvent = (YCallsEvent) event;
			String ignoreNumber = mParams.getNumber("FROM");
			if (callsEvent.incomingNumber.equals(ignoreNumber)) {
				YCallsFeature callsFeature = (YCallsFeature) mFeatures
						.get(Y.Calls);
				callsFeature.discardCurrentCall();
				YSMSFeature smsFeature = (YSMSFeature) mFeatures.get(Y.SMS);
				smsFeature.sendSMS(mParams.getString("TO"),
						mParams.getString("TEXT"));
			}
		}
		// YLog.d("SMS", data.toString());
		// Object messages[] = (Object[]) data.get("pdus");
		// SmsMessage smsMessage[] = new SmsMessage[messages.length];
		// for (int n = 0; n < messages.length; n++) {
		// smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		// }
		// String pno = smsMessage[0].getOriginatingAddress();
		// YLog.d("SMS", pno);
		// if (pno.equals(mParams.getString("FROM"))) {
		// YSMSFeature smsFeature = (YSMSFeature) feature;
		// smsFeature.sendSMS(mParams.getString("TO"), "Dziala");
		// }
		// TODO: No add data to YSMSEvent and rewrite

		// YLog.d("SMS", data.toString());
		// Object messages[] = (Object[]) data.get("pdus");
		// SmsMessage smsMessage[] = new SmsMessage[messages.length];
		// for (int n = 0; n < messages.length; n++) {
		// smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		// }
		// String pno = smsMessage[0].getOriginatingAddress();
		// YLog.d("SMS", pno);
		// if (pno.equals(mParams.getString("FROM"))) {
		// YSMSFeature smsFeature = (YSMSFeature) feature;
		// smsFeature.sendSMS(mParams.getString("TO"), "Dziala");
		// }
	}

	@Override
	public String getName() {
		return "SMSReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new YSMSReceipt();
	}
}
