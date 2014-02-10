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
package pl.poznan.put.cs.ify.app.ui.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

	public ArrayList<GroupModel> parseGetGroups(JSONArray source,
			String username) throws JSONException {
		ArrayList<GroupModel> result = new ArrayList<GroupModel>();
		for (int i = 0; i < source.length(); ++i) {
			JSONObject obj = source.getJSONObject(i);
			String groupName = obj.getString("name");
			String groupOwner = obj.getString("owner");
			boolean owner = groupOwner.equals(username);
			result.add(new GroupModel(groupName, groupOwner, owner));
		}
		return result;
	}

	public ArrayList<String> parseGetMyGroups(JSONArray source, String username)
			throws JSONException {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < source.length(); ++i) {
			JSONObject obj = source.getJSONObject(i);
			String groupName = obj.getString("name");
			String groupOwner = obj.getString("owner");
			boolean owner = groupOwner.equals(username);
			if (owner) {
				result.add(groupName);
			}
		}
		return result;
	}

	public ArrayList<String> parseGetInvitations(JSONArray source)
			throws JSONException {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < source.length(); ++i) {
			JSONObject obj = source.getJSONObject(i);
			result.add(obj.getString("name"));
		}
		return result;
	}

	public ArrayList<UserModel> parseGetUsers(JSONArray source)
			throws JSONException {
		ArrayList<UserModel> result = new ArrayList<UserModel>();
		for (int i = 0; i < source.length(); ++i) {
			JSONObject obj = source.getJSONObject(i);
			String firstName = obj.getString("firstName");
			String secondName = obj.getString("lastName");
			String nick = obj.getString("userName");
			result.add(new UserModel(firstName, secondName, nick));
		}
		return result;
	}
}
