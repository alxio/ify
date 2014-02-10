package cs.put.poznan.pl.summoner.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cs.put.poznan.pl.summoner.model.UserInfo;

public class UsersMapFragment extends SupportMapFragment {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm dd-MM-yyyy", Locale.getDefault());
	private static final String DATA = "cs.put.poznan.pl.summoner.data";
	private static final String CAMERA_POS = "cs.put.poznan.pl.summoner.camera-position";
	private ArrayList<UserInfo> mData;
	private GoogleMap mMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		mMap = getMap();
		ArrayList<UserInfo> tempData = getArguments().getParcelableArrayList(
				DATA);
		if (tempData != null) {
			mData = tempData;
			onDataUpdated();
		}
		return v;
	}

	public void updateData(ArrayList<UserInfo> data) {
		mData = data;
		getArguments().putParcelableArrayList(DATA, data);
		onDataUpdated();
	}

	@Override
	public void onResume() {
		super.onResume();
		onDataUpdated();
		getMap().setMyLocationEnabled(true);
		getMap().getUiSettings().setMyLocationButtonEnabled(true);
		retainCameraPosition();
	}

	private void retainCameraPosition() {
		CameraPosition cameraPosition = getArguments()
				.getParcelable(CAMERA_POS);
		if (cameraPosition != null) {
			getMap().moveCamera(
					CameraUpdateFactory.newCameraPosition(cameraPosition));
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		saveCameraPosition();
	}

	private void saveCameraPosition() {
		CameraPosition cameraPosition = getMap().getCameraPosition();
		getArguments().putParcelable(CAMERA_POS, cameraPosition);
	}

	private void onDataUpdated() {
		if (getMap() != null) {
			GoogleMap map = getMap();
			if (mData != null) {
				getMap().clear();
				for (UserInfo info : mData) {
					if (info.getLatLng() != null && info.getName() != null) {
						MarkerOptions markerOptions = new MarkerOptions();
						markerOptions.position(info.getLatLng());
						markerOptions.title(info.getName());
						String snippet = "";
						if (info.getUpdatedAtPos() != null) {
							snippet += dateFormat.format(new Date(info
									.getUpdatedAtPos())) + "\n";
						}
						if (info.getMessage() != null) {
							snippet += info.getMessage();
						}
						markerOptions.snippet(snippet);
						map.addMarker(markerOptions);
					}
				}
			}
		}
	}

	public static UsersMapFragment getNewInstance() {
		Bundle args = new Bundle();
		UsersMapFragment f = new UsersMapFragment();
		f.setArguments(args);
		return f;
	}

	public static UsersMapFragment getNewInstance(Bundle args) {
		UsersMapFragment f = new UsersMapFragment();
		f.setArguments(args);
		return f;
	}

	public void setSelectdPos(LatLng latLng) {
		CameraPosition cameraPosition = new CameraPosition(latLng, 15, 0, 0);
		getArguments().putParcelable(CAMERA_POS, cameraPosition);
	}
}
