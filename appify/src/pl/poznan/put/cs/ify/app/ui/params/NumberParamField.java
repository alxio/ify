package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.app.ui.params.contacts.NumberDialogFragment;
import pl.poznan.put.cs.ify.app.ui.params.contacts.NumberDialogFragment.OnNumberChosenListener;
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class NumberParamField extends ParamField implements
		OnNumberChosenListener {

	private NumberDialogFragment mPicker;
	private String mNumber;

	public NumberParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public YParam getFilledParam() {
		EditText editText = (EditText) findViewById(R.id.field_number);
		if (mNumber != null) {
			return new YParam(YParamType.Number, mNumber);
		} else {
			return new YParam(YParamType.Number, editText.getText().toString());

		}
	}

	public void setNumberPickerFragment(NumberDialogFragment d) {
		mPicker = d;
		mPicker.setOnDismissListener(this);
	}

	@Override
	public void onChosen(String name, String number) {
		EditText editText = (EditText) findViewById(R.id.field_number);
		mNumber = number;
	}

}
