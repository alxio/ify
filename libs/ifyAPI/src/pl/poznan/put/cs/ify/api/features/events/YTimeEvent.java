package pl.poznan.put.cs.ify.api.features.events;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.params.YDate;

public class YTimeEvent extends YEvent {

	private YDate mDate;

	/**
	 * Create event with given date.
	 * 
	 * @param d
	 *            date of event
	 */
	public YTimeEvent(YDate d) {
		mDate = d;
	}

	/**
	 * @return date connected with event.
	 */
	public YDate getDate() {
		return mDate;
	}

	@Override
	public long getId() {
		return Y.Time;
	}

}
