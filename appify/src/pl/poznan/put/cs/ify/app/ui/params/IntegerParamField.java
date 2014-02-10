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
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class IntegerParamField extends ParamField {

	public IntegerParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public YParam getFilledParam() {
		EditText integerEditText = (EditText) findViewById(R.id.field_integer);
		return new YParam(YParamType.Integer, Integer.valueOf(integerEditText
				.getText().toString()));
	}

	@Override
	public boolean isParamFilled() {
		EditText integerEditText = (EditText) findViewById(R.id.field_integer);
		return (!TextUtils.isEmpty(integerEditText.getText().toString()));
	}
}
