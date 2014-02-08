package pl.poznan.put.cs.ify.app.ui.server;

import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class DialogRegister extends DialogFragment {

	private EditText mFirstName;
	private EditText mSecondName;
	private EditText mNick;
	private EditText mPassword;
	private Button mRegisterButton;
	private Listener<String> mSuccessListener = new Listener<String>() {

		@Override
		public void onResponse(String arg0) {
			if (getActivity() != null) {
				if ("true".equals(arg0)) {
					Toast.makeText(getActivity(), "You may log in now",
							Toast.LENGTH_SHORT).show();
					getDialog().dismiss();
				} else if ("false".equals(arg0)) {
					Toast.makeText(getActivity(), "Nick already taken",
							Toast.LENGTH_SHORT).show();
				}
				mProgress.setVisibility(View.GONE);
				mRegisterButton.setVisibility(View.VISIBLE);
			}
		}
	};

	private ErrorListener mFaliureListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			mProgress.setVisibility(View.GONE);
			mRegisterButton.setVisibility(View.VISIBLE);
			if (getActivity() != null) {
				Toast.makeText(getActivity(),
						"Problems with internet connection", Toast.LENGTH_SHORT)
						.show();
			}
		}
	};
	private View mProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.register, null);
		mFirstName = (EditText) v.findViewById(R.id.et_register_firstname);
		mSecondName = (EditText) v.findViewById(R.id.et_register_secondname);
		mNick = (EditText) v.findViewById(R.id.et_register_nick);
		mPassword = (EditText) v.findViewById(R.id.et_register_pswd);
		mRegisterButton = (Button) v.findViewById(R.id.bRegister);
		mRegisterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doRegister();
			}
		});
		mProgress = v.findViewById(R.id.progress_register);
		getDialog().setTitle("Register");
		return v;
	}

	protected void doRegister() {
		String firstName = mFirstName.getText().toString();
		String secondName = mSecondName.getText().toString();
		String nick = mNick.getText().toString();
		String pswd = mPassword.getText().toString();

		ServerURLBuilder b = new ServerURLBuilder(getActivity());
		String registerURL = b.register(nick, pswd, firstName, secondName);

		performRegister(registerURL);
	}

	private void performRegister(String registerURL) {
		Log.d("REST_API", "registerURL " + registerURL);
		StringRequest r = new StringRequest(Method.POST, registerURL,
				mSuccessListener, mFaliureListener);
		mProgress.setVisibility(View.VISIBLE);
		mRegisterButton.setVisibility(View.GONE);
		QueueSingleton.getInstance(getActivity()).add(r);
	}
}
