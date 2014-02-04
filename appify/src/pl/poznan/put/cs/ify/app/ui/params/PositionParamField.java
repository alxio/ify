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
