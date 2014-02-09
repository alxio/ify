package pl.poznan.put.cs.ify.api.features.events;

import android.net.NetworkInfo;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YWifiEvent extends YEvent {
	public enum WiFi_EventType {
		WIFI_ENABLED, WIFI_DISABLED, CONNECTED, CONNECTING, DISCONNECTED, DISCONNECTING, UNKNOWN
	}

	private final WiFi_EventType mEventType;
	private String mSSID;

	public YWifiEvent(NetworkInfo.State state) {
		super();
		switch (state) {
		case CONNECTED:
			mEventType = WiFi_EventType.CONNECTED;
			break;
		case CONNECTING:
			mEventType = WiFi_EventType.CONNECTING;
			break;
		case DISCONNECTED:
			mEventType = WiFi_EventType.DISCONNECTED;
			break;
		case DISCONNECTING:
			mEventType = WiFi_EventType.DISCONNECTING;
			break;
		default:
			mEventType = WiFi_EventType.UNKNOWN;
			break;
		}

	}

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
