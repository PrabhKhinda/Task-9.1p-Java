package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button advertBtn = findViewById(R.id.advert_btn);
        Button lostFoundBtn = findViewById(R.id.lostfound_btn);

        advertBtn.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, NewAdvertActivity.class);
        startActivity(intent);
    });

        lostFoundBtn.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, LostFoundActivity.class);
        startActivity(intent);
    });
    }
}
