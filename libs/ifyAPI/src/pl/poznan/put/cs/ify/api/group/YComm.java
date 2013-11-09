package pl.poznan.put.cs.ify.api.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.features.YUserData;
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

	private void sendData(int tag, String target, Map<String, YParam> data) {
		YCommData commData = new YCommData(tag, target, mUserData);
		if (data != null)
			commData.setValues(data);
		mHost.sendData(commData);
	}

	public void sendVariable(String name, YParam data) {
		sendData(YCommand.SEND_DATA, "", name, data);
	}

	public void sendVariables(Map<String, YParam> vars) {
		sendData(YCommand.SEND_DATA, "", vars);
	}

	public void getVariable(String name, String userId) {
		sendData(YCommand.GET_DATA, userId, name, new YParam(YParamType.Boolean, false));
	}

	public List<String> getUsersList() {
		return null;
	}

	public String getMyId() {
		return null;
	}

	public int getUserState(String userId) {
		return 0;
	}

	public void sendEvent(String userID, int tag) {
	}

	public void sendEvent(String userID, int tag, YParam data) {
	}

	public void sendEvent(String userID, int tag, Map<String, YParam> data) {
	}

	public void broadcastEvent(int tag) {
	}

	public void broadcastEvent(int tag, YParam data) {
	}

	public void broadcastEvent(int tag, Map<String, YParam> data) {
	}
}
