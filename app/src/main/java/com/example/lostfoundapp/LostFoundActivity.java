package com.example.lostfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LostFoundActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found);

        ImageButton lost_btn = findViewById(R.id.lost_btn);
        ImageButton found_btn = findViewById(R.id.found_btn);
        Button showmap_btn = findViewById(R.id.showmap_btn);

        Fragment lostfrag = new LostFragment();
        Fragment foundfrag = new FoundFragment();
        Fragment mapFragment = new MapFragment();

        lost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
                fragtrans.replace(R.id.fragmentContainer, lostfrag).commit();
            }
        });

        found_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
                fragtrans.replace(R.id.fragmentContainer, foundfrag).commit();
            }
        });

        showmap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragtrans = getSupportFragmentManager().beginTransaction();
                fragtrans.replace(R.id.fragmentContainer, mapFragment).commit();
            }
        });
    }
}
