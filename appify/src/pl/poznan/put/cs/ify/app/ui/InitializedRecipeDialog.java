package pl.poznan.put.cs.ify.app.ui;

import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.core.ActiveRecipeInfo;
import pl.poznan.put.cs.ify.api.core.YAbstractRecipeService;
import pl.poznan.put.cs.ify.api.log.YLogEntryList;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.ui.params.ParamField;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.YRecipesService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InitializedRecipeDialog extends DialogFragment {

	public interface CommInterface {
		void onDisableRecipe(int id);
	}

	private Button send_button;
	private EditText send_edittext;

	private CommInterface mCallback;

	private TextView mLogs = null;
	BroadcastReceiver mReceiver = null;
	ActiveRecipeInfo mInfo = null;

	@Override
	public void onResume() {
		super.onResume();
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				YLogEntryList logs = intent
						.getParcelableExtra(YAbstractRecipeService.Recipe_LOGS);
				String tag = intent
						.getStringExtra(YAbstractRecipeService.Recipe_TAG);
				if (mLogs != null && logs != null) {
					mLogs.setText(logs.timeAndMessages());
				}
				if (logs != null) {
					Log.d("YLOGS", tag + logs.size());
				}
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter
				.addAction(YAbstractRecipeService.ACTION_Recipe_LOGS_RESPONSE);
		getActivity().registerReceiver(mReceiver, intentFilter);

		if (mLogs != null) {
			requestLogs();
		}
	}

	private void requestLogs() {
		Intent i = new Intent();
		i.setAction(YRecipesService.ACTION_Recipe_LOGS);
		if (getActivity() != null)
			getActivity().sendBroadcast(i);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
	}

	public static InitializedRecipeDialog getInstance(ActiveRecipeInfo info) {
		InitializedRecipeDialog f = new InitializedRecipeDialog();
		Bundle args = new Bundle();
		args.putParcelable(YAbstractRecipeService.INFO, info);
		f.setArguments(args);
		return f;
	}

	public void onCancel(DialogInterface dialog) {
		if (getActivity() != null && mReceiver != null) {
			getActivity().unregisterReceiver(mReceiver);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.initialized_recipe_dialog, container);
		mInfo = getArguments().getParcelable(YAbstractRecipeService.INFO);
		TextView name = (TextView) v.findViewById(R.id.name);
		name.setText(mInfo.getName());

		TextView feats = (TextView) v.findViewById(R.id.feats);
		String featList = YFeatureList.maskToString(mInfo.getParams()
				.getFeatures());
		feats.setText("Used features: " + featList);

		initParams(v, mInfo.getParams(), inflater);
		mLogs = (TextView) v.findViewById(R.id.logs);

		if ((mInfo.getParams().getFeatures() & Y.Text) == 0) {
			v.findViewById(R.id.send_layout).setVisibility(View.GONE);
		} else {
			send_button = (Button) v.findViewById(R.id.send_button);
			send_edittext = (EditText) v.findViewById(R.id.send_edittext);
			send_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (send_edittext != null
							&& send_edittext.getText() != null)
						sendTextToRecipe(send_edittext.getText().toString());
					else
						requestLogs();
				}
			});
		}

		mLogs.setMovementMethod(new ScrollingMovementMethod());
		Button disable = (Button) v.findViewById(R.id.disable_button);
		disable.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCallback != null) {
					Bundle args = getArguments();
					ActiveRecipeInfo info = args
							.getParcelable(YAbstractRecipeService.INFO);
					int id = info.getId();
					mCallback.onDisableRecipe(id);
				}
				getDialog().cancel();
			}
		});
		requestLogs();
		return v;
	}

	private void sendTextToRecipe(String text) {
		Log.d("<Y>Sending text", "" + text);
		Intent i = new Intent();
		i.setAction(YAbstractRecipeService.ACTION_SEND_TEXT);
		i.putExtra(YAbstractRecipeService.INFO, mInfo);
		i.putExtra(YAbstractRecipeService.TEXT, text);
		getActivity().sendBroadcast(i);
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
		case Position:
			v = (ParamField) inflater.inflate(R.layout.field_position, null);
			break;
		case Boolean:
			v = (ParamField) inflater.inflate(R.layout.field_boolean, null);
		default:
			v = (ParamField) inflater.inflate(R.layout.field_string, null);
			EditText defaultET = (EditText) v.findViewById(R.id.field_string);
			if (value.getValue() != null) {
				defaultET.setText(value.getValue() + "");
			}
			defaultET.setEnabled(false);
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
