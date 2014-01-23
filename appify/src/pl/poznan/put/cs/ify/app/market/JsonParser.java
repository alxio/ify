package pl.poznan.put.cs.ify.app.market;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

	public List<MarketInfo> parseRecipesInfo(JSONArray response) {
		ArrayList<MarketInfo> result = new ArrayList<MarketInfo>();
		try {
			String name;
			String description;
			String url;
			String desc;
			ArrayList<MarketComment> comments;
			long timestamp;
			int l = response.length();
			for (int i = 0; i < l; ++i) {
				JSONObject info = response.getJSONObject(i);
				name = info.getString("name");
				description = info.getString("description");
				timestamp = info.getLong("ts");
				url = info.getString("url");
				comments = new ArrayList<MarketComment>();
				JSONArray commentsJson = info.getJSONArray("comments");
				for (int j = 0; j < commentsJson.length(); ++j) {
					JSONObject commentJson = commentsJson.getJSONObject(j);
					comments.add(new MarketComment(commentJson.getLong("ts"), commentJson.getString("comment"),
							commentJson.getInt("id"), commentJson.getString("name")));
				}
				MarketInfo marketInfo = new MarketInfo(name, timestamp, url, description, comments);
				result.add(marketInfo);
			}
		} catch (JSONException ex) {

		}
		return result;
	}
}
