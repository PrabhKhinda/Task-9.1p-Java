package com.example.lostfoundapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NewAdvertActivity extends AppCompatActivity {

    private EditText advertNameEditText;
    private EditText advertDescriptionEditText;
    private EditText advertDateEditText;
    private EditText advertLocationEditText;
    private EditText advertPhoneEditText;
    private RadioButton lostRadioButton;
    private FusedLocationProviderClient fusedLocationClient;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        advertNameEditText = findViewById(R.id.advertname);
        advertDescriptionEditText = findViewById(R.id.advertdescription);
        advertDateEditText = findViewById(R.id.advertdate);
        advertLocationEditText = findViewById(R.id.advertlocation);
        advertPhoneEditText = findViewById(R.id.advertphone);
        lostRadioButton = findViewById(R.id.lost_rbtn);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize the Places SDK
        Places.initialize(getApplicationContext(), "AIzaSyCSzDX9P48HQw3b-6EqNxOBJ4noHDr30fo");

        // Create a new Places client instance
        final PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(List.of(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
        }

        // Set up the place selection listener
        if (autocompleteFragment != null) {
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // Handle the selected place
                    String selectedLocation = place.getAddress();
                    double selectedLatitude = Objects.requireNonNull(place.getLatLng()).latitude;
                    double selectedLongitude = place.getLatLng().longitude;
                    if (latitude == null && longitude == null) {
                        latitude = selectedLatitude;
                        longitude = selectedLongitude;
                    }
                    advertLocationEditText.setText(selectedLocation);
                }

                @Override
                public void onError(@NonNull com.google.android.gms.common.api.Status status) {
                    // Handle any errors
                    Toast.makeText(NewAdvertActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button saveButton = findViewById(R.id.advertsave_btn);
        saveButton.setOnClickListener(v -> {
            String advertType = lostRadioButton.isChecked() ? "Lost" : "Found";
            String advertName = advertNameEditText.getText().toString();
            String advertDescription = advertDescriptionEditText.getText().toString();
            String advertDate = advertDateEditText.getText().toString();
            String advertLocation = advertLocationEditText.getText().toString();
            String advertPhone = advertPhoneEditText.getText().toString();

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            if (latitude == null && longitude == null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }

                            // Store the advert in the database
                            DatabaseHelper databaseHelper = new DatabaseHelper(NewAdvertActivity.this);
                            databaseHelper.insertAdvert(
                                    advertType,
                                    advertName,
                                    advertDescription,
                                    advertDate,
                                    advertLocation,
                                    advertPhone,
                                    latitude,
                                    longitude
                            );
                            Toast.makeText(NewAdvertActivity.this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewAdvertActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

        });

        ImageButton locationButton = findViewById(R.id.location_btn);
        locationButton.setOnClickListener(v -> {
            if (checkLocationPermission()) {
                getCurrentLocation();
            } else {
                requestLocationPermission();
            }
        });
    }

    private boolean checkLocationPermission() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int result = ContextCompat.checkSelfPermission(this, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        ActivityCompat.requestPermissions(this, new String[]{permission}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void getCurrentLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<android.location.Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        android.location.Address address = addresses.get(0);
                        String locationName = address.getAddressLine(0);
                        advertLocationEditText.setText(locationName);
                        Toast.makeText(this, "Location found", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
}
