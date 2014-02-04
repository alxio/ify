package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YWifiEvent extends YEvent {

	public enum WiFi_EventType {
		WIFI_ENABLED, WIFI_DISABLED, CONNECTION_ESTABLISHED, CONNECTION_LOST
	}

	private final WiFi_EventType mEventType;
	private String mSSID;

	public YWifiEvent(WiFi_EventType eventType) {
		super();
		mEventType = eventType;
	}

	public WiFi_EventType getEventType() {
		return mEventType;
	}

	@Override
	public long getId() {
		return Y.Wifi;
	}

	public void setSSID(String ssid) {
		mSSID = ssid;
	}

	public String getSSID() {
		return mSSID;
	}

}
