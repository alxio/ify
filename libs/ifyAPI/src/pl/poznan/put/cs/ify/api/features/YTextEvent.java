package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YTextEvent extends YEvent {
	private String mText;

	/**
	 * Creates event with given text.
	 */
	public YTextEvent(String text) {
		mText = text;
	}

	/**
	 * @return text connected with event
	 */
	public String getText() {
		return mText;
	}

	@Override
	public long getId() {
		return Y.Text;
	}

}
