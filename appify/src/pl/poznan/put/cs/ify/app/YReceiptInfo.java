package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;

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

	public YReceiptInfo(String name, YParamList params) {
		mName = name;
		mRequiredParams = params;
	}

	public YReceiptInfo(YReceipt receipt) {
		mName = receipt.getName();
		mRequiredParams = new YParamList();
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

	public static List<YReceiptInfo> listFromBundle(Bundle b) {
		List<YReceiptInfo> list = new ArrayList<YReceiptInfo>();
		for (String key : b.keySet()) {
			list.add(new YReceiptInfo(key, (YParamList) b.getParcelable(key)));
		}
		return list;
	}
}
