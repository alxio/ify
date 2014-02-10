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

import java.util.Date;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
<<<<<<< HEAD

=======
import pl.poznan.put.cs.ify.api.params.YDate;
/**
 * Created every minute.
 *
 */
>>>>>>> d8e225e19336ea08772c638e74ab834f5eae6b76
public class YTimeEvent extends YEvent {

	private Date mDate;

	/**
	 * Create event with given date.
	 * 
	 * @param d
	 *            date of event
	 */
	public YTimeEvent(Date d) {
		mDate = d;
	}

	/**
	 * @return date connected with event.
	 */
	public Date getDate() {
		return mDate;
	}

	@Override
	public long getId() {
		return Y.Time;
	}

}
