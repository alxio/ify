package pl.poznan.put.cs.ify.app.ui.server;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

	public ArrayList<String> parseGetGroups(JSONArray source)
			throws JSONException {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < source.length(); ++i) {
			JSONObject obj = source.getJSONObject(i);
			result.add(obj.getString("name"));
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
}
