package cs.put.poznan.pl.summoner.ify;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YIntentFeature;
import pl.poznan.put.cs.ify.api.features.events.YGPSEvent;
import pl.poznan.put.cs.ify.api.features.events.YTextEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YCommData;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.api.params.YPosition;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import cs.put.poznan.pl.summoner.model.UserInfo;

public class SummonerRecipe extends YRecipe {

	public static final String POSITION = "pos";
	public static final String POSITION_LAST_UPDATE = "ts-pos";
	public static final String MESSAGE = "msg";
	public static final String MESSAGE_LAST_UPDATE = "ts-msg";
	public static final String REFRESH = "cs.put.poznan.pl.summoner.refresh";

	private YComm mComm;
	private YParam mPositionParam;
	private YParam mPosUpdateTimeParam;
	private YParam mMessageParam;
	private YParam mMessageUpdateParam;

	private HashMap<String, YParam> map;

	@Override
	public long requestFeatures() {
		return Y.Group | Y.GPS | Y.Intent | Y.Text | Y.Time;
	}

	@Override
	public void requestParams(YParamList params) {
	}

	@Override
	protected void init() throws Exception {
		super.init();
		map = new HashMap<String, YParam>();
		mComm = getFeatures().getGroup().createPoolingComm(this,
				"summoner-debug", 0);
		mComm.getAllVariables();
	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		if (event.getId() == Y.GPS) {
			YGPSEvent ge = (YGPSEvent) event;
			onGpsEvent(ge);
		} else if (event.getId() == Y.Text) {
			YTextEvent textEvent = (YTextEvent) event;
			onTextEvent(textEvent);
		} else if (event.getId() == Y.Time) {
			onTimeEvent();
		} else if (event.getId() == Y.Group) {
			YGroupEvent groupEvent = (YGroupEvent) event;
			onGroupEvent(groupEvent);
		}
	}

	private void onGroupEvent(YGroupEvent groupEvent) {
		YCommData data = groupEvent.getData();
		if (data != null) {
			Map<String, YParam> values = data.getValues();
			if (values != null) {
				HashMap<String, UserInfo> dataMap = getVariablesMap(values);
				if (dataMap != null) {
					YIntentFeature intentFeature = (YIntentFeature) getFeatures()
							.get(Y.Intent);
					Bundle bundle = new Bundle();
					for (Entry<String, UserInfo> e : dataMap.entrySet()) {
						bundle.putParcelable(e.getKey(), e.getValue());
					}
					intentFeature.sendIntentBroadcast(bundle,
							SummonerService.DATA, null);
				}
			}
		}
	}

	private HashMap<String, UserInfo> getVariablesMap(Map<String, YParam> values) {
		String[] splitted;
		String userName;
		String paramName;
		HashMap<String, UserInfo> dataMap = new HashMap<String, UserInfo>();
		for (Entry<String, YParam> entry : values.entrySet()) {
			splitted = entry.getKey().split("@");
			paramName = splitted[0];
			userName = splitted[1];
			if (!dataMap.containsKey(userName)) {
				dataMap.put(userName, new UserInfo(userName));
			}
			UserInfo userInfo = dataMap.get(userName);
			YParam value = entry.getValue();
			if (paramName.equals(POSITION)) {
				YPosition pos = (YPosition) YParam.getValueFromString(
						(String) value.getValue().toString(),
						YParamType.Position);
				userInfo.setLatLgn(new LatLng(pos.lat, pos.lng));
			} else if (paramName.equals(POSITION_LAST_UPDATE)) {
				int posLastUpdate = (Integer) YParam.getValueFromString(
						(String) value.getValue(), YParamType.Integer);
				userInfo.setUpdatedAtPos(posLastUpdate * 1000l);
			} else if (paramName.equals(MESSAGE)) {
				String message = (String) YParam.getValueFromString(
						(String) value.getValue(), YParamType.String);
				userInfo.setMessage(message);
			} else if (paramName.equals(MESSAGE_LAST_UPDATE)) {
				int msgLastUpdate = (Integer) YParam.getValueFromString(
						(String) value.getValue(), YParamType.Integer);
				userInfo.setUpdatedAtMsg(msgLastUpdate * 1000l);
			}
		}
		return dataMap;
	}

	private void onTimeEvent() {
		mComm.getAllVariables();
	}
	
	private void sendData() {
		if (mPositionParam != null) {
			map.put(POSITION, mPositionParam);
		}
		if (mPosUpdateTimeParam != null) {
			map.put(POSITION_LAST_UPDATE, mPosUpdateTimeParam);
		}
		if (mMessageParam != null) {
			map.put(MESSAGE, mMessageParam);
		}
		if (mMessageUpdateParam != null) {
			map.put(MESSAGE_LAST_UPDATE, mMessageUpdateParam);
		}
		mComm.sendVariables(map);
	}

	private void onTextEvent(YTextEvent textEvent) {
		String message = textEvent.getText();
		if (message.equals(REFRESH)) {
			mComm.getAllVariables();
			return;
		}
		mMessageParam = new YParam(YParamType.String, message);
		long updateTime = Calendar.getInstance().getTimeInMillis() / 1000;
		mMessageUpdateParam = new YParam(YParamType.Integer, updateTime);
		sendData();
		mComm.getAllVariables();
	}

	private void onGpsEvent(YGPSEvent ge) {
		mPositionParam = new YParam(YParamType.Position, ge.getPosition());
		long updateTime = Calendar.getInstance().getTimeInMillis() / 1000;
		mPosUpdateTimeParam = new YParam(YParamType.Integer, updateTime);
		sendData();
		mComm.getAllVariables();
	}

	@Override
	public String getName() {
		return "SummonerRecipe";
	}

	@Override
	public YRecipe newInstance() {
		return new SummonerRecipe();
	}

}
