package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class StringParamField extends ParamField {

	public StringParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public YParam getFilledParam() {
		EditText editText = (EditText) findViewById(R.id.field_string);
		return new YParam(YParamType.String, editText.getText().toString());

	}

}
