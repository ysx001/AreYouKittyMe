package com.example.android.areyoukittyme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView displayCatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayCatName = (TextView) findViewById(R.id.cat_name_display);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();

        if (startingIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String textEntered = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
            displayCatName.setText(textEntered);
        }
    }

}
