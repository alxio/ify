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
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class ParamField extends LinearLayout {

	protected YParam mParam;
	private String mName;

	public ParamField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public abstract YParam getFilledParam();

	public void setYParam(YParam param) {
		mParam = param;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public abstract boolean isParamFilled();
}
