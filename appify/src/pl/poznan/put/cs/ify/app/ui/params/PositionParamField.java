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
import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class PositionParamField extends ParamField {

	protected PositionMapDialog mMapDialog;

	public PositionParamField(final Context context, AttributeSet attrs) {
		super(context, attrs);
		findViewById(R.id.field_pick_number).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

					}
				});
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	@Override
	public YParam getFilledParam() {
		if (mMapDialog != null) {
			return mMapDialog.getParam();
		} else {
			return null;
		}
	}

	public void setPositionMapDialog(PositionMapDialog d) {
		mMapDialog = d;
	}

	@Override
	public boolean isParamFilled() {
		if (mMapDialog != null) {
			return mMapDialog.getParam() != null;
		} else {
			return false;
		}
	}

}
