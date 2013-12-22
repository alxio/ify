package pl.poznan.put.cs.ify.api.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import pl.poznan.put.cs.ify.api.IYReceiptHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.security.User;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class YGroupFeature extends YFeature {
	public static final long ID = Y.Group;
	public static final int DEFAULT_PERIOD = 10;

	private Map<YComm, PoolingSolution> mPoolingSollutions;

	@Override
	public long getId() {
		return ID;
	}

	@Override
	protected void init(IYReceiptHost srv) {
		YLog.d("<Y>Group", "Group context:" + srv.getContext());
		mHost = srv;
		mPoolingSollutions = new HashMap<YComm, PoolingSolution>();
	}

	@Override
	public void uninitialize() {
	}

	public void unregisterReceipt(YReceipt receipt) {
		// Remove all PoolingSolutions
		for (YComm c : new ArrayList<YComm>(mPoolingSollutions.keySet())) {
			if (c.getRecipe() == receipt) {
				mPoolingSollutions.get(c).uninitialize();
				mPoolingSollutions.remove(c);
			}
		}
		super.unregisterReceipt(receipt);
	}

	/**
	 * Creates communication object that can be used to send data to server.
	 * This objects are automatically released when recipe is unregistered.
	 * 
	 * @param receipt
	 *            insert 'this' here
	 * @param group
	 *            name of group, should be receipt's parameter
	 * @param period
	 *            time between pooling server in seconds
	 * @return
	 */
	public YComm createPoolingComm(YReceipt receipt, String group, int period) {
		TelephonyManager t = (TelephonyManager) mHost.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		User u = mHost.getSecurity().getCurrentUser();
		String login = u == null ? "" : u.name;
		String pass = u == null ? "" : u.hash;
		YUserData user = new YUserData(receipt.getName(), login, t.getDeviceId(), group, pass);
		final YComm comm = new YComm(receipt, user, this);
		mPoolingSollutions.put(comm, new PoolingSolution(comm, mHost.getContext(), ((long) 1000) * period));
		return comm;
	}

	protected void sendData(YCommData commData, YComm comm) {
		PoolingSolution ps = mPoolingSollutions.get(comm);
		if (ps == null) {
			YLog.wtf(comm.getRecipe().getName(), "YComm not initialized");
		}
		try {
			JSONObject json = commData.toJsonObject();
			Log.d("COMM",json.toString());
			ps.sendJson(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
