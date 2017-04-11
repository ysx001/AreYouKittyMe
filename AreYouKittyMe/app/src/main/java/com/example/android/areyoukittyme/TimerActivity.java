package com.example.android.areyoukittyme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.areyoukittyme.utilities.NotificationUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button testNotificationButton;

    private TextView hourCountdown;
    private TextView minCountdown;
    private TextView secCountdown;
    private Button timerStartBtn;
    private Button timerPauseBtn;
    private EditText hourEditText;
    private Spinner minSpinner;

    private Thread t;
    private boolean isCountingdown = false;

    private int hour;
    private int minute;
    private int second = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        this.hourCountdown = (TextView) findViewById(R.id.hourCountdown);
        this.minCountdown = (TextView) findViewById(R.id.minCountdown);
        this.secCountdown = (TextView) findViewById(R.id.secCountdown);

        this.timerStartBtn = (Button) findViewById(R.id.timerStartBtn);
        timerStartBtn.setOnClickListener(this);
        this.timerPauseBtn = (Button) findViewById(R.id.timerPauseBtn);
        timerPauseBtn.setOnClickListener(this);

        this.hourEditText = (EditText) findViewById(R.id.hourEditText);
        this.minSpinner = (Spinner) findViewById(R.id.minSpinner);
        String[] items = new String[]{"Minutes", "00", "10", "20", "30", "40", "50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        this.minSpinner.setAdapter(adapter);

        // enable back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.timerStartBtn: {

                boolean hourExist = false;
                boolean minExist = false;

                try {
                    hour = Integer.parseInt(String.valueOf(hourEditText.getText()));
                    hourCountdown.setText(String.format("%02d", hour));
                    hourExist = (hour != 0);

                } catch (Exception e) {
                    hour = 0;
                    hourCountdown.setText("00");
                }

                try {
                    minute = Integer.parseInt(String.valueOf(minSpinner.getSelectedItem()));
                    minCountdown.setText(String.format("%02d", minute));
                    minExist = (minute != 0);

                } catch (Exception e) {
                    minute = 0;
                    minCountdown.setText("00");
                }

                isCountingdown = hourExist || minExist;

                if (isCountingdown) {
                    this.hourEditText.setEnabled(false);
                    this.minSpinner.setEnabled(false);

                    this.countdownThreadSetup();
                }

                break;

            }
            case R.id.timerPauseBtn: {
                if (isCountingdown) {
                    isCountingdown = false;
                    this.t.interrupt();
                }
                else {
                    isCountingdown = true;
                    this.countdownThreadSetup();
                }
            }
        }
    }

    private void countdownThreadSetup() {

        this.t = new Thread() {

            @Override
            public void run() {

                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewCountdown();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }

        };

        this.t.start();
    }

    private void textViewCountdown() {

        TextView temp = (TextView) findViewById(R.id.dotTextView);
        temp.setText(":");

        if (this.second > 0) {
            this.second --;
        }
        else {
            if (this.minute > 0) {
                this.second = 59;
                this.minute --;
            }
            else {
                if (this.hour > 0) {
                    this.second = 59;
                    this.minute = 59;
                    this.hour --;
                }
                else {
                    this.t.interrupt();
                }
            }
        }

        this.hourCountdown.setText(String.format("%02d", this.hour));
        this.minCountdown.setText(String.format("%02d", this.minute));
        this.secCountdown.setText(String.format("%02d", this.second));
    }

    // triggers notification for testing
    public void testNotification(View view) {
        NotificationUtils.remindUserSwitchBack(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationUtils.remindUserSwitchBack(this);
    }
}
