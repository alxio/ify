package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.params.YDate;

public class YTimeEvent extends YEvent{
	
	private YDate mDate;

	public YTimeEvent(YDate d) {
		mDate = d;
	}
	
	public YDate getDate() {
		return mDate;
	}
	
	@Override
	public long getId() {
		return Y.Time;
	}

}
