package pl.poznan.put.cs.ify.app.ui;

import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.log.YLogEntry;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.types.YList;
import pl.poznan.put.cs.ify.api.types.YLogEntryList;
import pl.poznan.put.cs.ify.app.ui.InitializedReceiptDialog.CommInterface;
import pl.poznan.put.cs.ify.app.ui.params.ParamField;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActiveReceiptInfo;
import pl.poznan.put.cs.ify.core.YReceiptsService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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

	private TextView mLogs = null;
	BroadcastReceiver mReceiver = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				YLogEntryList logs = intent.getParcelableExtra(YReceiptsService.RECEIPT_LOGS);
				String tag = intent.getStringExtra(YReceiptsService.RECEIPT_TAG);
				if (mLogs != null) {
					mLogs.setText(Html.fromHtml(logs.toHTML()));
				}
				Log.d("YLOGS", tag + logs.size());
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(YReceiptsService.ACTION_RECEIPT_LOGS_RESPONSE);
		getActivity().registerReceiver(mReceiver, intentFilter);

		if (mLogs != null) {
			requestLogs(getActivity());
		}
	}

	private void requestLogs(Context ctx) {
		Intent activeReceiptsRequest = new Intent();
		activeReceiptsRequest.setAction(YReceiptsService.ACTION_GET_RECEIPTS_REQUEST);
		ctx.sendBroadcast(activeReceiptsRequest);
	}

	public static InitializedReceiptDialog getInstance(ActiveReceiptInfo info) {
		InitializedReceiptDialog f = new InitializedReceiptDialog();
		Bundle args = new Bundle();
		args.putParcelable(INFO, info);
		f.setArguments(args);
		return f;
	}

	public void onCancel(DialogInterface dialog) {
		if (getActivity() != null && mReceiver != null) {
			getActivity().unregisterReceiver(mReceiver);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.initialized_receipt_dialog, container);
		ActiveReceiptInfo info = getArguments().getParcelable(INFO);
		TextView name = (TextView) v.findViewById(R.id.name);
		name.setText(info.getName());
		initParams(v, info.getParams(), inflater);
		mLogs = (TextView) v.findViewById(R.id.logs);
		mLogs.setMovementMethod(new ScrollingMovementMethod());
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

		if (getActivity() != null) {
			requestLogs(getActivity());
		}

		return v;
	}

	private void initParams(View v, YParamList params, LayoutInflater inflater) {
		ViewGroup container = (ViewGroup) v.findViewById(R.id.params_container);
		for (Entry<String, YParam> optional : params) {
			View field = initField(optional, inflater);
			container.addView(field);
		}
	}

	// Almost copy-paste from options dialog.
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

	public void setLogsData(String data) {
		mLogs.setText(data);
	}
}
