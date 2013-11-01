package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.api.log.YLog;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsActivity extends YActivity {

	private CompoundButton mToggleLogs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		mToggleLogs = (CompoundButton) findViewById(R.id.log_toggle);
		mToggleLogs.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					YLog.show();
				} else {
					YLog.hide();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mToggleLogs.setChecked(YLog.isVisible());
	}

}
