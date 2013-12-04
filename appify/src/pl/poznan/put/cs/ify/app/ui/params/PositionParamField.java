package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import android.content.Context;
import android.util.AttributeSet;

public class PositionParamField extends ParamField {

	protected PositionMapDialog mMapDialog;

	public PositionParamField(final Context context, AttributeSet attrs) {
		super(context, attrs);
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

}
