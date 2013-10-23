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
}
