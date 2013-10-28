package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;
import android.content.Context;
import android.util.AttributeSet;

public class BooleanParamField extends ParamField {

	public BooleanParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public YParam getFilledParam() {
		// TODO Auto-generated method stub
		return new YParam(YParamType.Boolean, true);
	}

}
