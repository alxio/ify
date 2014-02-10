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
package pl.poznan.put.cs.ify.api.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.core.ISecurity;
import pl.poznan.put.cs.ify.api.group.YGroupFeature;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Class responsible for users authentication and storage of passwords.
 */
public class YSecurity implements ISecurity {
	/**
	 * Defines ability to handle information about login events.
	 */
	public interface ILoginCallback {
		void onLoginSuccess(String username);

		void onLoginFail(String message);

		void onLogout();
	}

	private class RequestCallback implements Listener<String>, ErrorListener {
		public RequestCallback(ILoginCallback callback, User user) {
			mCallback = callback;
			mUser = user;
		}

		private ILoginCallback mCallback;
		private User mUser;

		@Override
		public void onErrorResponse(VolleyError arg0) {
			mCallback.onLoginFail("Connecting to server failed");
		}

		@Override
		public void onResponse(String arg0) {
			Log.e("ON_RESP", arg0);
			if ("true".equals(arg0)) {
				setCurrentUser(mUser);
				mCallback.onLoginSuccess(mUser.name);
			} else
				mCallback.onLoginFail(new String(
						"Login or password is incorrect"));
		}
	}

	public static String PREFS_NAME = "pl.poznan.put.cs.ify.api.security.SecurityManager";
	public static String NAME = "NAME";
	public static String HASH = "HASH";
	public static String ALGO = "SHA1";

	public YSecurity(Context ctx) {
		mContext = ctx;
	}

	private User currentUser = null;
	private Context mContext = null;

	/**
	 * @see pl.poznan.put.cs.ify.api.core.ISecurity#login(java.lang.String, java.lang.String, pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback)
	 */
	@Override
	public void login(String username, String password, ILoginCallback cb) {
		User user = createUser(username, password);

		RequestQueue q = QueueSingleton.getInstance(mContext);
		RequestCallback proxy = new RequestCallback(cb, user);
		StringRequest request = new StringRequest(Method.POST,
				YGroupFeature.getServerUrl(mContext) + "login/" + user.name
						+ "/" + user.hash, proxy, proxy);
		q.add(request);
	}
	
	/**
	 * @see pl.poznan.put.cs.ify.api.core.ISecurity#logout(pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback)
	 */
	@Override
	public void logout(ILoginCallback callback) {
		setCurrentUser(null);
		callback.onLogout();
	}
	
	private void setCurrentUser(User user) {
		currentUser = user;
		saveUser(user);
	}

	@Override
	public User getCurrentUser() {
		if (currentUser == null) {
			currentUser = readUser();
		}
		return currentUser;
	}

	private static String hash(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance(ALGO);
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

	private static String getHash(String user, String pass) {
		try {
			return hash(user + pass);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(ALGO + " not supported");
		}
	}

	/**
	 * Reads User from {@link android.content.SharedPreferences
	 * SharedPreferences}
	 * 
	 * @param ctx
	 *            Context needed to use SharedPreferences
	 * @return User or null
	 */
	private User readUser() {
		PreferencesProvider prefs = PreferencesProvider.getInstance(mContext);
		String name = prefs.getString(PreferencesProvider.KEY_USERNAME);
		String hash = prefs.getString(PreferencesProvider.KEY_HASH);
		return new User(name, hash);
	}

	/**
	 * Creates User based on login and password. If login is empty both
	 * user.name and user.hash will be null.
	 * 
	 * @return user
	 */
	public static User createUser(String login, String pass) {
		return User.create(login, getHash(login, pass));
	}

	/**
	 * Saves User to {@link android.content.SharedPreferences SharedPreferences}
	 * 
	 * @param user
	 *            User to save
	 * @param ctx
	 *            Context needed to use SharedPreferences
	 */
	private void saveUser(User user) {
		PreferencesProvider prefs = PreferencesProvider.getInstance(mContext);
		if (user != null) {
			prefs.putString(PreferencesProvider.KEY_USERNAME, user.name);
			prefs.putString(PreferencesProvider.KEY_HASH, user.hash);
		} else {
			prefs.putString(PreferencesProvider.KEY_USERNAME,
					PreferencesProvider.DEFAULT_STRING);
			prefs.putString(PreferencesProvider.KEY_HASH,
					PreferencesProvider.DEFAULT_STRING);
		}
	}

}
