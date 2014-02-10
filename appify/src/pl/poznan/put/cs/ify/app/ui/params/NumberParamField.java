/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.app.ui.params.contacts.NumberDialogFragment;
import pl.poznan.put.cs.ify.app.ui.params.contacts.NumberDialogFragment.OnNumberChosenListener;
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.text.TextUtils;
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
		editText.setText(number);
		mNumber = number;
	}

	@Override
	public boolean isParamFilled() {
		EditText editText = (EditText) findViewById(R.id.field_number);
		return (!TextUtils.isEmpty(editText.getText().toString()));
	}
}
