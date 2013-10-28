package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class IntegerParamField extends ParamField {

	public IntegerParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public YParam getFilledParam() {
		EditText integerEditText = (EditText) findViewById(R.id.field_integer);
		return new YParam(YParamType.Integer, Integer.valueOf(integerEditText.getText().toString()));
	}

}
