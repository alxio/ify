package pl.poznan.put.cs.ify.api.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;

public class YSecurity {
	public interface ILoginCallback {
		void onLoginSuccess(String username);

		void onLoginFail(String message);
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

	public void login(String username, String password, ILoginCallback cb) {
		User user = createUser(username, password);
		// TODO: check in server if password is ok
		setCurrentUser(user);
		cb.onLoginSuccess(username);
	}

	public void logout() {
		setCurrentUser(null);
	}

	public boolean setCurrentUser(User user) {
		currentUser = user;
		return saveUser(user);
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
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
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
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String name = settings.getString(NAME, null);
		String hash = settings.getString(HASH, null);
		return User.create(name, hash);
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
	private boolean saveUser(User user) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		if (user == null)
			user = new User();
		editor.putString(NAME, user.name);
		editor.putString(HASH, user.hash);
		return editor.commit();
	}
}