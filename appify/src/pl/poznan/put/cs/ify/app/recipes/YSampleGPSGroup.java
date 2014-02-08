package pl.poznan.put.cs.ify.app.recipes;

import java.util.Map;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.events.YGPSEvent;
import pl.poznan.put.cs.ify.api.group.YComm;
import pl.poznan.put.cs.ify.api.group.YCommand;
import pl.poznan.put.cs.ify.api.group.YGroupEvent;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;

/**
 * Recipe activated by GPS location saving it on server and showing other users
 * position.
 */
public class YSampleGPSGroup extends YRecipe {
	@Override
	public long requestFeatures() {
		return Y.GPS | Y.Group | Y.Text;
	}

	private YComm comm;

	@Override
	public void requestParams(YParamList params) {
		params.add("GROUP", YParamType.String, "");
	}

	public void init() {
		comm = getFeatures().getGroup().createPoolingComm(this,
				getParams().getString("GROUP"), 10);
	}

	@Override
	public void handleEvent(YEvent event) {
		if (event.getId() == Y.GPS) {
			YGPSEvent e = (YGPSEvent) event;
			// Create YParam based on GPS position
			YParam position = YParam.createPosition(e.getPosition());
			// Send position to server
			comm.sendVariable("pos", position);
			// Get others' variables
			comm.getAllVariables();
			comm.getUsersList();
		}
		if (event.getId() == Y.Group) {
			YGroupEvent e = (YGroupEvent) event;
			Map<String, YParam> map = e.getData().getValues();
			if (e.getData().getEventTag() == YCommand.GET_USER_LIST) {
				Log.i("User names:");
			} else {
				Log.i("User locations:");
			}
			for (Map.Entry<String, YParam> en : map.entrySet()) {
				String message = en.getKey() + ": " + en.getValue().getValue().toString();
				Log.i(message);
			}
		}
	}

	@Override
	public String getName() {
		return "YSampleGPSGroup";
	}

	@Override
	public YRecipe newInstance() {
		return new YSampleGPSGroup();
	}

}
