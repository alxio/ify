package pl.poznan.put.cs.ify.appify.receipts;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YFeatureList;
import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.features.YAudioManagerFeature;
import pl.poznan.put.cs.ify.api.features.YTimeEvent;
import pl.poznan.put.cs.ify.api.features.YTimeFeature;
import pl.poznan.put.cs.ify.api.params.YDate;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YTimeRingerReceipt extends YReceipt{

	@Override
	public void requestFeatures(YFeatureList features) {
		features.add(new YTimeFeature());
		features.add(new YAudioManagerFeature());
	}

	@Override
	public void requestParams(YParamList params) {
		
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.Time) {
			YTimeEvent timeEvent = (YTimeEvent) event;
			YDate date = timeEvent.getDate();
			int hours = date.getHours();
			YAudioManagerFeature audioManager = (YAudioManagerFeature) mFeatures.get(Y.AudioManager);
			if (hours > 0 && hours < 8) {
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
