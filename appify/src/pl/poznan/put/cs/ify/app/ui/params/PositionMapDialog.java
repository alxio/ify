package pl.poznan.put.cs.ify.app.ui.params;

import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamType;
import pl.poznan.put.cs.ify.api.params.YPosition;
import pl.poznan.put.cs.ify.appify.R;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class PositionMapDialog extends DialogFragment {

	private static final int DEFAULT_RADIUS = 20;
	private static final int FILL_COLOR = 0x88000000;
	protected LatLng mCenter;
	private SupportMapFragment mMapFrag;
	protected Circle mCircle;
	private SeekBar mSeekBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NORMAL, R.style.AppTheme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_map, container);
		mMapFrag = SupportMapFragment.newInstance();
		FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.map_container, mMapFrag);
		fragmentTransaction.commit();
		initSeekBar(view);
		return view;
	}

	private void initSeekBar(View view) {
		mSeekBar = (SeekBar) view.findViewById(R.id.radius_seekbar);
		mSeekBar.setMax(500);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (mCircle == null) {
					return;
				}
				mCircle.setRadius(progress);
			}
		});
		mSeekBar.setProgress(DEFAULT_RADIUS);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		setupMap();
	}

	private void setupMap() {
		final GoogleMap map = mMapFrag.getMap();
		if (map != null) {
			setupMap(map);
		} else {
			getView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					GoogleMap map = mMapFrag.getMap();
					if (map != null) {
						getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
						setupMap(map);
					}
				}
			});
		}
	}

	private void setupMap(final GoogleMap map) {
		map.setMyLocationEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {

			private boolean centered = false;

			@Override
			public void onMyLocationChange(Location location) {
				if (!centered && location != null) {
					centered = true;
					map.animateCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(location.getLatitude(), location.getLongitude()), 15));
				}
			}
		});

		map.setOnMapClickListener(new OnMapClickListener() {



			@Override
			public void onMapClick(LatLng point) {
				if (mCircle != null) {
					mCircle.remove();
					mCircle = null;
				}
				mCenter = point;
				CircleOptions circleOptions = new CircleOptions().center(point).fillColor(FILL_COLOR)
						.radius(DEFAULT_RADIUS);
				mCircle = map.addCircle(circleOptions);
				mSeekBar.setProgress(DEFAULT_RADIUS);
			}
		});
	}

	public YParam getParam() {
		return new YParam(YParamType.YPosition, new YPosition(mCenter.latitude, mCenter.longitude, mSeekBar.getProgress()));
	}
}
