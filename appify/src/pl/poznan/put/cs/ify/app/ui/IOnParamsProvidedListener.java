package pl.poznan.put.cs.ify.app.ui;

import pl.poznan.put.cs.ify.api.params.YParamList;

public interface IOnParamsProvidedListener {

	public void onParamsProvided(YParamList requiredParams,
			YParamList optionalParams, String receipt);
}
