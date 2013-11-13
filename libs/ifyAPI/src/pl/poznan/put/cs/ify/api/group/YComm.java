package pl.poznan.put.cs.ify.api.group;

import java.util.HashMap;
import java.util.Map;

import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YComm {
	private YUserData mUserData;
	private YGroupFeature mHost;
	public static final String BROADCAST = "BROADCAST";

	public YComm(YUserData user, YGroupFeature host) {
		mUserData = user;
		mHost = host;
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
		if (map != null)
			commData.setValues(map);
		mHost.sendData(commData);
	}

	public void sendVariable(String name, YParam data) {
		sendData(YCommand.SEND_DATA, "", name, data);
	}

	public void sendVariables(Map<String, YParam> map) {
		sendData(YCommand.SEND_DATA, "", map);
	}

	public void getVariable(String name, String userId) {
		sendData(YCommand.GET_DATA, userId, name, new YParam(YParamType.Boolean, false));
	}

	public void getUsersList() {
		sendData(YCommand.GET_USER_LIST, "");
	}

	public String getMyId() {
		return mUserData.getId();
	}

	public void sendEvent(String target, int tag) {
		if (tag > 0)
			sendData(tag, target);
		else
			YLog.w(mUserData.getReceipt(), "sendEvent(): Invalid tag");
	}

	public void sendEvent(String target, int tag, String dataName, YParam data) {
		if (tag > 0)
			sendData(tag, target, dataName, data);
		else
			YLog.w(mUserData.getReceipt(), "sendEvent(): Invalid tag");
	}

	public void sendEvent(String target, int tag, Map<String, YParam> map) {
		if (tag > 0)
			sendData(tag, target, map);
		else
			YLog.w(mUserData.getReceipt(), "sendEvent(): Invalid tag");
	}

	public void broadcastEvent(int tag) {
		sendEvent(BROADCAST, tag);
	}

	public void broadcastEvent(int tag, String dataName, YParam data) {
		sendData(tag, BROADCAST, dataName, data);
	}

	public void broadcastEvent(int tag, Map<String, YParam> map) {
		sendData(tag, BROADCAST, map);
	}
}
