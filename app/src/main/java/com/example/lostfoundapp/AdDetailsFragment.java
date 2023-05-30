package com.example.lostfoundapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AdDetailsFragment extends Fragment {
    private Advert advert;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ad_details, container, false);

        assert getArguments() != null;
        advert = getArguments().getParcelable("selectedAdvert");

        // Display advert details in the corresponding views
        TextView textViewName = rootView.findViewById(R.id.text_view_name);
        textViewName.setText(advert.getName());

        TextView textViewType = rootView.findViewById(R.id.text_view_type);
        textViewType.setText(advert.getType());

        // ... Display values of other columns
        TextView textViewDescription = rootView.findViewById(R.id.text_view_description);
        textViewDescription.setText(advert.getDescription());

        TextView textViewDate = rootView.findViewById(R.id.text_view_date);
        textViewDate.setText(advert.getDate());

        TextView textViewLocation = rootView.findViewById(R.id.text_view_location);
        textViewLocation.setText(advert.getLocation());

        TextView textViewPhone = rootView.findViewById(R.id.text_view_phone);
        textViewPhone.setText(advert.getPhone());

        // Display latitude and longitude
        TextView textViewLatitude = rootView.findViewById(R.id.text_view_latitude);
        textViewLatitude.setText(String.valueOf(advert.getLatitude()));

        TextView textViewLongitude = rootView.findViewById(R.id.text_view_longitude);
        textViewLongitude.setText(String.valueOf(advert.getLongitude()));

        // Add click listener to remove button
        Button removeButton = rootView.findViewById(R.id.button_remove);
        removeButton.setOnClickListener(v -> {
            removeAdvertFromDatabase();
            goBackToPreviousFragment();
        });

        return rootView;
    }

    private void removeAdvertFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        databaseHelper.removeAdvert(Integer.parseInt(String.valueOf(advert.getId())));
    }

    private void goBackToPreviousFragment() {
        getParentFragmentManager().popBackStack();
    }
}
