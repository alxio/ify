package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAudioManagerFeature;
import pl.poznan.put.cs.ify.api.features.events.YTimeEvent;
import pl.poznan.put.cs.ify.api.params.YDate;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

public class YTimeRingerReceipt extends YReceipt {

	@Override
	public long requestFeatures() {
		return Y.Time | Y.AudioManager;
	}

	@Override
	public void requestParams(YParamList params) {
		params.add("SILENT_FROM", YParamType.Integer, 0);
		params.add("SILENT_TO", YParamType.Integer, 8);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Time) {
			YTimeEvent timeEvent = (YTimeEvent) event;
			YDate date = timeEvent.getDate();
			@SuppressWarnings("deprecation")
			int hours = date.getHours();
			YAudioManagerFeature audioManager = (YAudioManagerFeature) mFeatures.get(Y.AudioManager);
			int silentFrom = mParams.getInteger("SILENT_FROM");
			int silentTo = mParams.getInteger("SILENT_TO");
			if (hours >= silentFrom && hours < silentTo) {
				audioManager.setSilent();
			} else {
				audioManager.setNormal();
			}
		}
	}

	@Override
	public String getName() {
		return "YTimeRingerReceipt";
	}

	@Override
	public YReceipt newInstance() {
		return new YTimeRingerReceipt();
	}

}
