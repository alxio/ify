package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.core.YReceiptInfo;
import pl.poznan.put.cs.ify.params.YParam.Type;
import pl.poznan.put.cs.ify.params.YParamList;

public class SMSReceiptInfo extends YReceiptInfo {

	public static String PHONE_NUMBER = "PHONE_NUMBER";
	public static String ANSWER = "ANSWER";

	public SMSReceiptInfo() {
		super();
		setName("SMSReceipt");
		YParamList requiredParams = new YParamList();
		requiredParams.add(PHONE_NUMBER, Type.String);
		requiredParams.add(ANSWER, Type.String);
		setRequiredParams(requiredParams);
	}
}
