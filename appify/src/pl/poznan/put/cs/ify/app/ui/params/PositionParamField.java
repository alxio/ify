package pl.poznan.put.cs.ify.app.ui.params;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.appify.R;

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
