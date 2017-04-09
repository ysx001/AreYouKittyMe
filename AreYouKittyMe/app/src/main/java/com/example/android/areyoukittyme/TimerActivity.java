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

        this.timerStartBtn = (Button) findViewById(R.id.timerBackBtn);

        timerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Store the context variable
                Context context = TimerActivity.this;

                // This is the class we want to start and open when button is clicked
                Class destActivity = MainActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);
            }
        });
    }
}
