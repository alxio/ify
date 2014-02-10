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

import android.text.TextUtils;

public class User {

	public User() {
	}

	/**
	 * @param name users name (login)
	 * @param hash hash of password
	 */
	public User(String name, String hash) {
		this.name = name;
		this.hash = hash;
	}

	public String name;
	public String hash;

	/**
	 * @return true if name and hash are not null
	 */
	public static boolean valid(User u) {
		return u != null && u.isValid();
	}
	/**
	 * @return true if name and hash are not null
	 */
	public boolean isValid() {
		return name != null && hash != null;
	}

	/**
	 * @param name users name (login)
	 * @param hash hash of password
	 * @return valid user or null
	 * @see #isValid()
	 */
	public static User create(String name, String hash) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(hash)) {
			return null;
		}
		return new User(name, hash);
	}
}
