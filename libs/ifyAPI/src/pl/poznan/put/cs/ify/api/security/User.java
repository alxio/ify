package pl.poznan.put.cs.ify.api.security;

import android.text.TextUtils;

public class User {

	public User() {
	}

	public User(String name, String hash) {
		this.name = name;
		this.hash = hash;
	}

	public String name;
	public String hash;

	public static boolean valid(User u) {
		return u != null && u.isValid();
	}

	public boolean isValid() {
		return name != null && hash != null;
	}

	public static User create(String name, String hash) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(hash)) {
			return null;
		}
		return new User(name, hash);
	}
}
