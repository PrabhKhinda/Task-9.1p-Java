package com.example.lostfoundapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class LostFragment extends Fragment {

    private ListView adsListView;
    private List<Advert> adsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lost, container, false);

        adsListView = rootView.findViewById(R.id.ads_list_view);
        adsList = retrieveLostAdsFromDatabase();

        ArrayAdapter<Advert> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, adsList);
        adsListView.setAdapter(adapter);

        adsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Advert selectedAdvert = adsList.get(position);
                showAdDetailsFragment(selectedAdvert);
            }
        });

        return rootView;
    }

    private void showAdDetailsFragment(Advert advert) {
        AdDetailsFragment fragment = new AdDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedAdvert", advert);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private List<Advert> retrieveLostAdsFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        return databaseHelper.getLostAds();
    }
}
