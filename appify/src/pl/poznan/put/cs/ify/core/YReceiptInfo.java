package pl.poznan.put.cs.ify.core;

import pl.poznan.put.cs.ify.params.YParamList;

/**
 * Represents basic informations needed to create and present receipt.
 * 
 * @author Mateusz Sikora
 * 
 */
public abstract class YReceiptInfo {
	private String mName;
	private YParamList mRequiredParams;

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
