package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.core.YReceiptInfo;
import pl.poznan.put.cs.ify.params.YParam.Type;
import pl.poznan.put.cs.ify.params.YParamList;
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
