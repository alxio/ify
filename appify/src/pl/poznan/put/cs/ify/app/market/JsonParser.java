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
			Integer safe;
			Double rate;
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
				safe = info.getInt("safe");
				if (info.isNull("rate")) {
					rate = null;
				} else {
					rate = info.getDouble("rate");
				}
				JSONArray commentsJson = info.getJSONArray("comments");
				for (int j = 0; j < commentsJson.length(); ++j) {
					JSONObject commentJson = commentsJson.getJSONObject(j);
					comments.add(new MarketComment(commentJson.getLong("ts"), commentJson.getString("comment"),
							commentJson.getInt("id"), commentJson.getString("name")));
				}
				MarketInfo marketInfo = new MarketInfo(name, timestamp, url, description, comments, safe, rate);
				result.add(marketInfo);
			}
		} catch (JSONException ex) {

		}
		return result;
	}
}
