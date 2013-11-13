package pl.poznan.put.cs.ify.app.ui;

import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.ui.InitializedReceiptDialog.CommInterface;
import pl.poznan.put.cs.ify.app.ui.params.ParamField;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActiveReceiptInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InitializedReceiptDialog extends DialogFragment {
	
	public interface CommInterface {
		void onDisableReceipt(int id);
	}
	
	private CommInterface mCallback;
	private static final String INFO = "INFO";

	public static InitializedReceiptDialog getInstance(ActiveReceiptInfo info) {
		InitializedReceiptDialog f = new InitializedReceiptDialog();
		Bundle args = new Bundle();
		args.putParcelable(INFO, info);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.initialized_receipt_dialog,
				container);
		ActiveReceiptInfo info = getArguments().getParcelable(INFO);
		TextView name = (TextView) v.findViewById(R.id.name);
		name.setText(info.getName());
		initParams(v, info.getParams(), inflater);
		TextView logs = (TextView) v.findViewById(R.id.logs);
		logs.setMovementMethod(new ScrollingMovementMethod());
		Button disable = (Button) v.findViewById(R.id.disable_button);
		disable.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCallback != null) {
					mCallback.onDisableReceipt(((ActiveReceiptInfo) getArguments().getParcelable("INFO")).getId());
				}
				getDialog().cancel();
			}
		});
		return v;
	}

	private void initParams(View v, YParamList params, LayoutInflater inflater) {
		ViewGroup container = (ViewGroup) v.findViewById(R.id.params_container);
		for (Entry<String, YParam> optional : params) {
			View field = initField(optional, inflater);
			container.addView(field);
		}
	}
	
	//Almost copy-paste from options dialog.
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
			integerET.setEnabled(false);
			break;
		case String:
			v = (ParamField) inflater.inflate(R.layout.field_string, null);
			EditText stringET = (EditText) v.findViewById(R.id.field_string);
			if (value.getValue() != null) {
				stringET.setText(value.getValue() + "");
			}
			stringET.setEnabled(false);
			break;
		case YPosition:
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

	public void setCommInterface(CommInterface commInterface) {
		mCallback = commInterface;
	}
}
