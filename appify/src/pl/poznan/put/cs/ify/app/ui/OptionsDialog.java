package pl.poznan.put.cs.ify.app.ui;

import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.ui.params.ParamField;
import pl.poznan.put.cs.ify.app.ui.params.PositionMapDialog;
import pl.poznan.put.cs.ify.app.ui.params.PositionParamField;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OptionsDialog extends DialogFragment {

	private YParamList mRequiredParams;
	private YParamList mOptionalParams;
	private long mFeatures;
	private String mReceiptName;
	private OnClickListener lonInitClickedListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (validate(v)) {
				YParamList requiredResult = new YParamList();
				int viewsCount = requiredContainer.getChildCount();
				for (int i = 0; i < viewsCount; ++i) {
					ParamField view = (ParamField) requiredContainer.getChildAt(i);
					requiredResult.add(view.getName(), view.getFilledParam());
				}
				YParamList optionalResult = new YParamList();
				viewsCount = optionalContainer.getChildCount();
				for (int i = 0; i < viewsCount; ++i) {
					ParamField view = (ParamField) optionalContainer.getChildAt(i);
					optionalResult.add(view.getName(), view.getFilledParam());
				}
				if (mListener != null) {
					mListener.onParamsProvided(requiredResult, optionalResult, mReceiptName);
				}
			}
			getDialog().cancel();
		}
	};
	private IOnParamsProvidedListener mListener;
	private ViewGroup requiredContainer;
	private ViewGroup optionalContainer;
	private TextView featuresView;

	/**
	 * TODO: This is not safe, its not ensured that params will be available
	 * after recreation of fragment. Possible solution: Make YParamList
	 * implement Parcelable interface and pass it as arguments.
	 * 
	 * @param required
	 * @param optional
	 */
	public static OptionsDialog getInstance(YParamList required, YParamList optional, String name, long features) {
		OptionsDialog f = new OptionsDialog();
		f.setData(required, optional, features);
		f.setName(name);
		return f;
	}

	protected boolean validate(View v) {
		// TODO Auto-generated method stub
		return true;
	}

	public void setOnParamsProvidedListener(IOnParamsProvidedListener listener) {
		mListener = listener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void setName(String name) {
		mReceiptName = name;
	}

	private void setData(YParamList required, YParamList optional, long features) {
		mRequiredParams = required;
		mOptionalParams = optional;
		mFeatures = features;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.options_dialog, null);
		featuresView = (TextView) v.findViewById(R.id.options_features);
		requiredContainer = (ViewGroup) v.findViewById(R.id.options_required_container);
		optionalContainer = (ViewGroup) v.findViewById(R.id.options_optional_container);
		
		String featList = YFeatureList.maskToString(mFeatures);
		featuresView.setText("Used features: " + featList);
		
		if (mRequiredParams != null) {
			for (Entry<String, YParam> required : mRequiredParams) {
				View field = initField(required, inflater);
				requiredContainer.addView(field);
			}
		}
		if (mOptionalParams != null) {
			for (Entry<String, YParam> optional : mOptionalParams) {
				View field = initField(optional, inflater);
				requiredContainer.addView(field);
			}
		}
		getDialog().setTitle(mReceiptName);
		Button initButton = (Button) v.findViewById(R.id.init_button);
		initButton.setOnClickListener(lonInitClickedListener);
		return v;
	}

	private View initField(Entry<String, YParam> entry, LayoutInflater inflater) {
		ParamField v = null;
		String name = entry.getKey();
		YParam value = entry.getValue();
		TextView nameTextView;
		switch (value.getType()) {
		case Integer:
			v = (ParamField) inflater.inflate(R.layout.field_integer, null);
			EditText integerET = (EditText) v.findViewById(R.id.field_integer);
			if (value.getValue() != null) {
				integerET.setText(value.getValue() + "");
			}

			break;
		case Group:
		case String:
			v = (ParamField) inflater.inflate(R.layout.field_string, null);
			EditText stringET = (EditText) v.findViewById(R.id.field_string);
			if (value.getValue() != null) {
				stringET.setText(value.getValue() + "");
			}
			break;
		case Position:
			v = (ParamField) inflater.inflate(R.layout.field_position, null);
			v.findViewById(R.id.field_button_showmap).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					PositionMapDialog d = new PositionMapDialog();
					ft.add(d, "MAP");
					ft.commit();
					((PositionParamField) v.getParent()).setPositionMapDialog(d);

				}
			});
			break;
		case Boolean:
			v = (ParamField) inflater.inflate(R.layout.field_boolean, null);
		default:
			break;
		}
		nameTextView = (TextView) v.findViewById(R.id.field_name);
		nameTextView.setText(name);
		v.setYParam(value);
		v.setName(name);
		return v;
	}

}
