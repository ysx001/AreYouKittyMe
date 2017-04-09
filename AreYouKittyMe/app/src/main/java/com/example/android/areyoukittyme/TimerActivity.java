package com.example.android.areyoukittyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // enable back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
