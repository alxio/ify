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
import android.content.Context;
import android.telephony.TelephonyManager;

public class YGroupFeature extends YFeature {
	public static final String USERNAME = "alx";
	public static final int ID = Y.Group;
	public static final int DEFAULT_PERIOD = 10;

	private Map<YComm, PoolingSolution> mPoolingSollutions;

	@Override
	public int getId() {
		return ID;
	}

	@Override
	protected void init(IYReceiptHost srv) {
		mContext = srv.getContext();
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
		TelephonyManager t = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		YUserData user = new YUserData(receipt.getName(), USERNAME, t.getDeviceId(), group);
		final YComm comm = new YComm(receipt, user, this);
		mPoolingSollutions.put(comm, new PoolingSolution(comm, mContext, ((long) 1000) * period));
		return comm;
	}

	protected void sendData(YCommData commData, YComm comm) {
		PoolingSolution ps = mPoolingSollutions.get(comm);
		if (ps == null) {
			YLog.wtf(comm.getRecipe().getName(), "YComm not initialized");
		}
		try {
			JSONObject json = commData.toJsonObject();
			ps.sendJson(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
