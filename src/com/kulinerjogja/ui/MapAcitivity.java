package com.kulinerjogja.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kulinerjogja.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kulinerjogja.utils.LogManager;

public class MapAcitivity extends FragmentActivity {

	private SupportMapFragment fragment;
	private GoogleMap map;
	private LatLng lokasi;
	private double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		String lati = getIntent().getExtras().getString("lat");
		String longi = getIntent().getExtras().getString("long");
		LogManager.print("latlong sesudah : " + lati + " | " + longi);
		latitude = Double.parseDouble(lati);
		longitude = Double.parseDouble(longi);
		lokasi = new LatLng(latitude, longitude);

		initmap();
	}

	private void initmap() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragment = (SupportMapFragment) fragmentManager
				.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fragmentManager.beginTransaction().replace(R.id.map, fragment)
					.commit();
		}
		if (map == null) {
			map = fragment.getMap();
			if (map != null) {
				setupcamera();
				setupmap();
			}
		}
	}

	private void setupmap() {
		map.setMyLocationEnabled(true);
		map.addMarker(new MarkerOptions().position(lokasi));
	}

	private void setupcamera() {
		CameraUpdate center=CameraUpdateFactory.newLatLng(lokasi);
		    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
		    map.moveCamera(center);
		    map.animateCamera(zoom);
	}
	@Override
	protected void onResume() {
		super.onResume();
		initmap();
	}
}
