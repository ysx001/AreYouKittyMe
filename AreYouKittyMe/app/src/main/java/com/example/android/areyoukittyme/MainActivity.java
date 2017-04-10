package com.example.android.areyoukittyme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView displayCatName;
    private Button statsButton;
    private Button vocabButton;
    private Button storeButton;
    private Button timerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayCatName = (TextView) findViewById(R.id.cat_name_display);
        statsButton = (Button) findViewById(R.id.stats_button);
        vocabButton = (Button) findViewById(R.id.vocab_button);
        storeButton = (Button) findViewById(R.id.store_button);
        timerButton = (Button) findViewById(R.id.timer_button);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();

        if (startingIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String textEntered = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
            displayCatName.setText(textEntered);
        }

        // Setting an OnClickLister for the statsButton
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Store the context variable
                Context context = MainActivity.this;

                // This is the class we want to start and open when button is clicked
                Class destActivity = StatsActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        // Setting an OnClickLister for the vocabButton
        vocabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Store the context variable
                Context context = MainActivity.this;

                // This is the class we want to start and open when button is clicked
                Class destActivity = VocabActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        // Setting an OnClickLister for the storeButton
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Store the context variable
                Context context = MainActivity.this;

                // This is the class we want to start and open when button is clicked
                Class destActivity = StoreActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        // Setting an OnClickLister for the timerButton
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Store the context variable
                Context context = MainActivity.this;

                // This is the class we want to start and open when button is clicked
                Class destActivity = TimerActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                // This is the class we want to start and open when button is clicked
                Class destActivity = StoreActivity.class;
                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);
                startActivity(startMainActivityIntent);
            }
        });
    }

}
