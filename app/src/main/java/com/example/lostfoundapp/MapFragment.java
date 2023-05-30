package com.example.lostfoundapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        // Customize the map as needed
        // Enable zoom controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Mark lost and found adverts on the map
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        List<Advert> lostAds = databaseHelper.getLostAds();
        List<Advert> foundAds = databaseHelper.getFoundAds();
        LatLng latLng;

        for (Advert ad : lostAds) {
            latLng = new LatLng(ad.getLatitude(), ad.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Lost: " + ad.getType()));
        }

        for (Advert ad : foundAds) {
            latLng = new LatLng(ad.getLatitude(), ad.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Found: " + ad.getType()));
        }

        // Move the camera to a suitable position
        // You can customize the camera position and zoom level based on your requirements
        LatLng defaultLatLng = new LatLng(37.7749, -122.4194); // Example: San Francisco coordinates
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 9f));
    }
}
