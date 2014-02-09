/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
