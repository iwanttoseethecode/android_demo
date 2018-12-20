package com.example.luoling.android_dome.customCoordinator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.example.luoling.android_dome.R;

public class CustomCoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_coordinator_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("动脑学院");
        setSupportActionBar(toolbar);
    }
}
