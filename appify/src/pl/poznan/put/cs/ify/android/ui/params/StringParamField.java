package pl.poznan.put.cs.ify.android.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class StringParamField extends ParamField {

	public StringParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public YParam getFilledParam() {
		EditText editText = (EditText) findViewById(R.id.field_string);
		return new YParam(mParam.getType(), editText.getText().toString());

	}

}
