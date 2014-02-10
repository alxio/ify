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
package pl.poznan.put.cs.ify.api.features.events;

import android.net.NetworkInfo;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
/**
 * Represents change of WiFi state.
 *
 */
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
