package pl.poznan.put.cs.ify.app.fragments;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SettingsFragment extends Fragment {

	private EditText mServerUrl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_settings, null);
		mServerUrl = (EditText) v.findViewById(R.id.et_server);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		String serverUrl = prefs.getString(PreferencesProvider.KEY_SERVER_URL);
		mServerUrl.setText(serverUrl);
	}

	@Override
	public void onPause() {
		super.onPause();
		PreferencesProvider prefs = PreferencesProvider
				.getInstance(getActivity());
		String serverUrl = prefs.getString(PreferencesProvider.KEY_SERVER_URL);
		prefs.putString(PreferencesProvider.KEY_SERVER_URL, mServerUrl
				.getText().toString());
		if (!serverUrl.equals(mServerUrl.getText().toString())) {
			((MainActivity) getActivity()).onServerUrlChanged();
		}

	}
}
