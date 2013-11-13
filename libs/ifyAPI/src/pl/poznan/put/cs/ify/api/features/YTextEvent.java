package pl.poznan.put.cs.ify.api.features;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;

public class YTextEvent extends YEvent {
	private String mText;

	public YTextEvent(String text) {
		mText = text;
	}

	public String getText() {
		return mText;
	}

	@Override
	public int getId() {
		return Y.Text;
	}

}
