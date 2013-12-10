package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YCallsEvent;
import pl.poznan.put.cs.ify.api.features.YCallsFeature;
import pl.poznan.put.cs.ify.api.features.YSMSEvent;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YSMSReceipt extends YReceipt {

	@Override
	public long requestFeatures() {
		return Y.SMS | Y.Calls;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("FROM", YParamType.String, "+48606932226");
		params.add("TO", YParamType.String, "+48792571392");
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Calls) {
			YCallsFeature callsFeature = (YCallsFeature) mFeatures.get(Y.Calls);
			callsFeature.discardCurrentCall();
		}
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
