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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.PreferencesProvider;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.security.User;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class YGroupFeature extends YFeature {
	public static final long ID = Y.Group;
	public static final int DEFAULT_PERIOD = 10;
	Context ctx;
	
	private Map<YComm, PoolingSolution> mPoolingSollutions;

	public User getCurrentUser() {
		return mHost.getSecurity().getCurrentUser();
	}

	public void changeGroupServer(String url) {
		for (Entry<YComm, PoolingSolution> e : mPoolingSollutions.entrySet()) {
			e.getValue().setServerUrl(url);
		}
	}

	@Override
	public long getId() {
		return ID;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		YLog.d("<Y>Group", "Group context:" + srv.getContext());
		ctx = srv.getContext();
		mHost = srv;
		mPoolingSollutions = new HashMap<YComm, PoolingSolution>();
	}

	@Override
	public void uninitialize() {
	}

	// TODO: Move
	public static String getServerUrl(Context ctx) {
		PreferencesProvider prefs = PreferencesProvider.getInstance(ctx);
		return prefs.getString(PreferencesProvider.KEY_SERVER_URL);
	}

	public void unregisterRecipe(YRecipe recipe) {
		// Remove all PoolingSolutions
		for (YComm c : new ArrayList<YComm>(mPoolingSollutions.keySet())) {
			if (c.getRecipe() == recipe) {
				mPoolingSollutions.get(c).uninitialize();
				mPoolingSollutions.remove(c);
			}
		}
		super.unregisterRecipe(recipe);
	}

	/**
	 * Creates communication object that can be used to send data to server.
	 * This objects are automatically released when recipe is unregistered.
	 * 
	 * @param recipe
	 *            insert 'this' here
	 * @param group
	 *            name of group, should be recipe's parameter
	 * @param period
	 *            time between pooling server in seconds
	 * @return
	 */
	public YComm createPoolingComm(YRecipe recipe, String group, int period) {
		TelephonyManager t = (TelephonyManager) mHost.getContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		User u = mHost.getSecurity().getCurrentUser();
		String login = u == null ? "" : u.name;
		String pass = u == null ? "" : u.hash;
		YUserData user = new YUserData(recipe.getName(), login,
				t.getDeviceId(), group, pass);
		final YComm comm = new YComm(recipe, user, this);
		PoolingSolution poolingSolution = new PoolingSolution(comm,
				mHost.getContext(), ((long) 1000) * period,
				getServerUrl(mHost.getContext()));
		mPoolingSollutions.put(comm, poolingSolution);
		return comm;
	}

	protected void sendData(YCommData commData, YComm comm) {
		Log.d("POOLDEBUG", "sendData() called");
		try {
			Log.d("OMGDEBUG", "sendData() " + commData.toJsonObject());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		PoolingSolution ps = mPoolingSollutions.get(comm);
		if (ps == null) {
			YLog.wtf(comm.getRecipe().getName(), "YComm not initialized");
		}
		try {
			JSONObject json = commData.toJsonObject();
			Log.d("COMM", json.toString());
			ps.sendJson(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
