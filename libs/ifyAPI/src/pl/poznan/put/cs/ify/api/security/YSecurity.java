package pl.poznan.put.cs.ify.api.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.core.ISecurity;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;

import android.content.Context;
import android.content.SharedPreferences;

public class YSecurity implements ISecurity {
	public interface ILoginCallback {
		void onLoginSuccess(String username);

		void onLoginFail(String message);

		void onLogout();
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

	@Override
	public void login(String username, String password, ILoginCallback cb) {
		User user = createUser(username, password);
		
		RequestQueue queue = QueueSingleton.getInstance(mContext);
		// TODO: check in server if password is ok
		setCurrentUser(user);
		cb.onLoginSuccess(username);
	}

	public void logout(ILoginCallback callback) {
		setCurrentUser(null);
		callback.onLogout();
	}

	public void setCurrentUser(User user) {
		currentUser = user;
		saveUser(user);
	}

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
			return hash(user);
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