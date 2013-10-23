package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YSMSFeature;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceipt extends YReceipt {

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YSMSFeature());
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("FROM", YParam.Type.String, "+48606932226");
		params.add("TO", YParam.Type.String, "+48792571392");

	}

	@Override
	public void handleData(YFeature feature, Bundle data) {
		Log.d("SMS", data.toString());
		Object messages[] = (Object[]) data.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int n = 0; n < messages.length; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		}
		String pno = smsMessage[0].getOriginatingAddress();
		Log.d("SMS", pno);
		if (pno.equals(mParams.getString("FROM"))) {
			YSMSFeature smsFeature = (YSMSFeature) feature;
			smsFeature.sendSMS(mParams.getString("TO"), "Dziala");
		}

	}

	@Override
	public String getName() {
		return "SMSReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new SMSReceipt();
	}
}
