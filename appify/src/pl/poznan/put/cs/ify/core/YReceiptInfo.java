package pl.poznan.put.cs.ify.core;

import pl.poznan.put.cs.ify.api.features.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;

/**
 * Represents basic informations needed to create and present receipt.
 * 
 * @author Mateusz Sikora
 * 
 */
public class YReceiptInfo {
	private String mName;
	private YParamList mRequiredParams;

	@Deprecated
	protected YReceiptInfo() {
	}

	public YReceiptInfo(YReceipt receipt) {
		mName = receipt.getName();
		receipt.requestParams(mRequiredParams);
	}

	public void setName(String name) {
		mName = name;
	}

	public void setRequiredParams(YParamList requiredParams) {
		mRequiredParams = requiredParams;
	}

	public void setOptionalParams(YParamList optionalParams) {
		mOptionalParams = optionalParams;
	}

	private YParamList mOptionalParams;

	public String getName() {
		return mName;
	}

	public YParamList getRequiredParams() {
		return mRequiredParams;
	}

	public YParamList getOptionalParams() {
		return mOptionalParams;
	}

}
