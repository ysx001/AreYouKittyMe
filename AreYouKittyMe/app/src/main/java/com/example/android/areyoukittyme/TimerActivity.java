package com.example.android.areyoukittyme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TimerActivity extends AppCompatActivity {

    private Button timerStartBtn;
    private Button timerBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        this.timerStartBtn = (Button) findViewById(R.id.timerStartBtn);
        this.timerStartBtn = (Button) findViewById(R.id.timerStartBtn);

        // enable back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
