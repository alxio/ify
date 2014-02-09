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
package pl.poznan.put.cs.ify.app.ui.params;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class PositionMap extends SupportMapFragment {

	private static final int DEFAULT_RADIUS = 20;
	private static final int FILL_COLOR = 0x88000000;
	protected LatLng mCenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		setupMap();
		return v;
	}

	private void setupMap() {
		final GoogleMap map = getMap();
		if (map != null) {
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
					mCenter = point;
					CircleOptions circleOptions = new CircleOptions().center(point).fillColor(FILL_COLOR)
							.radius(DEFAULT_RADIUS);
					map.addCircle(circleOptions);
				}
			});
		}
	}

	public static PositionMap getInstance() {
		return new PositionMap();
	}
}
