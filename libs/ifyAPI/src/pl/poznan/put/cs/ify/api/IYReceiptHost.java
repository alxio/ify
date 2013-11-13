package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.params.YParamList;
import android.content.Context;

public interface IYReceiptHost {

	public abstract int enableReceipt(String name, YParamList params);

	public abstract void disableReceipt(Integer id);

	public abstract Context getContext();
	
	public abstract void sendLogs(String tag);
}