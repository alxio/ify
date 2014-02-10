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

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YCallsEvent extends YEvent {

	private final String state;
	private final String incomingNumber;

	public static final String OFFHOOK = "OFFHOOK";
	public static final String IDLE = "IDLE";
	public static final String RINGING = "RINGING";

	public YCallsEvent(String string, String incomingNumber) {
		this.state = string;
		this.incomingNumber = incomingNumber;
	}

	@Override
	public long getId() {
		return Y.Calls;
	}

	public String getIncomingNumber() {
		return incomingNumber;
	}

	public String getState() {
		return state;
	}
}
