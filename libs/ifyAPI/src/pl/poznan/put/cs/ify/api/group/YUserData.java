package pl.poznan.put.cs.ify.api.group;

import org.json.JSONException;
import org.json.JSONObject;

public class YUserData {
	public static final String RECIPE = "recipe";
	public static final String USERNAME = "username";
	public static final String GROUP = "group";
	public static final String DEVICE = "device";
	public static final String PASSWORD = "password";

	private String mRecipeName;
	private String mUserName;
	private String mDeviceName;
	private String mGroupId;
	private String mPassword;

	public YUserData(String recipeName, String userName, String deviceName, String groupId, String password) {
		mRecipeName = recipeName;
		mUserName = userName;
		mDeviceName = deviceName;
		mGroupId = groupId;
		mPassword = password;
	}

	private YUserData() {
	}

	public static YUserData fromJsonObject(JSONObject json) {
		try {
			YUserData data = new YUserData();
			data.mDeviceName = json.getString(DEVICE);
			data.mGroupId = json.getString(GROUP);
			data.mRecipeName = json.getString(RECIPE);
			data.mUserName = json.getString(USERNAME);
			data.mPassword = json.getString(PASSWORD);
			return data;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject toJsonObject() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(DEVICE, mDeviceName);
		json.put(GROUP, mGroupId);
		json.put(RECIPE, mRecipeName);
		json.put(USERNAME, mUserName);
		json.put(PASSWORD, mPassword);
		return json;
	}

	public String getId() {
		return mUserName;
	}

	public String getReceipt() {
		return mRecipeName;
	}
}
