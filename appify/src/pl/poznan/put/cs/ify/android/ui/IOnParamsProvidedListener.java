package pl.poznan.put.cs.ify.android.ui;

import pl.poznan.put.cs.ify.api.params.YParamList;

public interface IOnParamsProvidedListener {
	
	public void onRequiredParamsProvided(YParamList required);
	public void onOptionalParamsProvoded(YParamList optional);

}
