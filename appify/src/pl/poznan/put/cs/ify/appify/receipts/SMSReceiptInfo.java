package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.params.YParam.Type;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.core.YReceiptInfo;
//TODO: Unimplemented
public class SMSReceiptInfo extends YReceiptInfo {

	public static String PHONE_NUMBER = "PHONE_NUMBER";
	public static String ANSWER = "ANSWER";

	public SMSReceiptInfo() {
		super();
		setName("SMSReceipt");
		YParamList requiredParams = new YParamList();
		requiredParams.add(PHONE_NUMBER, Type.String, "+48606932226");
		requiredParams.add(ANSWER, Type.String, "Sorry, i'm busy right now");
		setRequiredParams(requiredParams);
	}
}
