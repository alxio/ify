package pl.poznan.put.cs.ify.android.ui;

import java.util.Map.Entry;

import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.UnimplementedException;
import pl.poznan.put.cs.ify.params.YParam;
import pl.poznan.put.cs.ify.params.YParamList;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OptionsDialog extends DialogFragment {

	private YParamList mRequiredParams;
	private YParamList mOptionalParams;
	private String mName;

	/**
	 * TODO: This is not safe, its not ensured that params will be available
	 * after recreation of fragment. Possible solution: Make YParamList
	 * implement Parcelable interface and pass it as arguments.
	 * 
	 * @param required
	 * @param optional
	 */
	public static OptionsDialog getInstance(YParamList required,
			YParamList optional, String name) {
		OptionsDialog f = new OptionsDialog();
		f.setData(required, optional);
		f.setName(name);
		return f;
	}

	private void setName(String name) {
		mName = name;
	}

	private void setData(YParamList required, YParamList optional) {
		mRequiredParams = required;
		mOptionalParams = optional;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.options_dialog, null);
		ViewGroup requiredContainer = (ViewGroup) v
				.findViewById(R.id.options_required_container);
		ViewGroup optionalContainer = (ViewGroup) v
				.findViewById(R.id.options_optional_container);
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
		getDialog().setTitle(mName);

		return v;
	}

	private View initField(Entry<String, YParam> entry, LayoutInflater inflater) {
		View v = null;
		String name = entry.getKey();
		YParam value = entry.getValue();
		TextView nameTextView;
		TextView valueTextView;
		switch (value.getType()) {
		case Integer:
			v = inflater.inflate(R.layout.field_integer, null);
			nameTextView = (TextView) v.findViewById(R.id.field_name);
			nameTextView.setText(name);
			v.setTag(entry);
			break;
		case String:
			v = inflater.inflate(R.layout.field_string, null);
			nameTextView = (TextView) v.findViewById(R.id.field_name);
			nameTextView.setText(name);
			v.setTag(entry);
			break;
		case YPosition:
			break;
		default:
			break;
		}
		return v;
	}

}
