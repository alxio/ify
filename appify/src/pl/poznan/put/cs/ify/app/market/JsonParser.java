package pl.poznan.put.cs.ify.app.market;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

	public List<MarketInfo> parseReceiptsInfo(JSONArray response) {
		ArrayList<MarketInfo> result = new ArrayList<MarketInfo>();
		try {
			String name;
			String description;
			String url;
			long timestamp;
			int l = response.length();
			for (int i = 0; i < l; ++i) {
				JSONObject info = response.getJSONObject(i);
				name = info.getString("name");
				description = info.getString("description");
				timestamp = info.getLong("ts");
				url = info.getString("url");
				MarketInfo marketInfo = new MarketInfo(name, timestamp, url, description);
				result.add(marketInfo);
			}
		} catch (JSONException ex) {

		}
		return result;
	}
}
