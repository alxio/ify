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
package pl.poznan.put.cs.ify.api.group;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.network.QueueSingleton;
import pl.poznan.put.cs.ify.api.params.YParam;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class YComm {
	private YRecipe mRecipe;
	private YUserData mUserData;
	private YGroupFeature mHost;
	public static final String BROADCAST = "BROADCAST";

	public YComm(YRecipe recipe, YUserData user, YGroupFeature host) {
		mRecipe = recipe;
		mUserData = user;
		mHost = host;
	}

	protected YRecipe getRecipe() {
		return mRecipe;
	}

	private void sendData(int tag, String target) {
		sendData(tag, target, null);
	}

	private void sendData(int tag, String target, String dataName, YParam data) {
		Map<String, YParam> map = new HashMap<String, YParam>();
		map.put(dataName, data);
		sendData(tag, target, map);
	}

	private void sendData(int tag, String target, Map<String, YParam> map) {
		YCommData commData = new YCommData(tag, target, mUserData);
		if (map != null) {
			if (tag == YCommand.SEND_DATA) {
				commData.setValuesAddingUser(map, mUserData.getId());
			} else {
				commData.setValues(map);
			}
		}
		mHost.sendData(commData, this);
	}

	public void sendVariable(String name, YParam data) {
		sendData(YCommand.SEND_DATA, "", name, data);
	}

	public void sendVariables(Map<String, YParam> map) {
		sendData(YCommand.SEND_DATA, "", map);
	}

	public void getVariables(String userId) {
		sendData(YCommand.GET_DATA, userId);
	}

	public void getAllVariables() {
		sendData(YCommand.GET_DATA, null);
	}

	public String getMyId() {
		return mUserData.getId();
	}

	public void sendEvent(String target, int tag) {
		if (tag > 0)
			sendData(tag, target);
		else
			YLog.w(mUserData.getRecipe(), "sendEvent(): Invalid tag");
	}

	public void sendEvent(String target, int tag, String dataName, YParam data) {
		if (tag > 0)
			sendData(tag, target, dataName, data);
		else
			YLog.w(mUserData.getRecipe(), "sendEvent(): Invalid tag");
	}

	public void sendEvent(String target, int tag, Map<String, YParam> map) {
		if (tag > 0)
			sendData(tag, target, map);
		else
			YLog.w(mUserData.getRecipe(), "sendEvent(): Invalid tag");
	}

	public void broadcastEvent(int tag) {
		sendEvent(BROADCAST, tag);
	}

	public void pool() {
		Log.d("OMGDEBUG", "pooling ");
		sendData(YCommand.POOLING, BROADCAST);
	}

	public void broadcastEvent(int tag, String dataName, YParam data) {
		sendData(tag, BROADCAST, dataName, data);
	}

	public void broadcastEvent(int tag, Map<String, YParam> map) {
		sendData(tag, BROADCAST, map);
	}

	public YCommData getPoolRequest() {
		return new YCommData(YCommand.POOLING, BROADCAST, mUserData);
	}

	protected boolean deliverEvent(YGroupEvent event) {
		return mRecipe.tryHandleEvent(event);
	}

	private class RequestCallback implements Listener<String>, ErrorListener {
		public RequestCallback(YUserData user) {
			mUser = user;
		}

		private YUserData mUser;

		@Override
		public void onErrorResponse(VolleyError arg0) {
		}

		@Override
		public void onResponse(String arg0) {
			try {
				JSONArray json = new JSONArray(arg0);
				YCommData data = new YCommData(YCommand.GET_USER_LIST,
						mUser.getId(), mUser);
				for (int i = 0; i < json.length(); i++) {
					JSONObject jObject = json.getJSONObject(i);
					String login = jObject.getString("userName");
					String name = jObject.getString("firstName") + " "
							+ jObject.getString("lastName");
					data.add(login, YParam.createString(name));
				}
				YGroupEvent ev = new YGroupEvent(data);
				deliverEvent(ev);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void getUsersList() {
		RequestQueue q = QueueSingleton.getInstance(mHost.ctx);
		RequestCallback proxy = new RequestCallback(mUserData);
		StringRequest request = new StringRequest(Method.GET,
				YGroupFeature.getServerUrl(mHost.ctx) + "groups/members/"
						+ mUserData.getGroup() + "/" + mUserData.getId() + "/"
						+ mUserData.getPassword(), proxy, proxy);
		q.add(request);
	}
}
