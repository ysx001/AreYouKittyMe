package com.example.android.areyoukittyme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import static android.app.PendingIntent.getActivity;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView hourCountdown;
    private TextView minCountdown;
    private TextView secCountdown;
    private Button timerStartBtn;
    private Button timerPauseBtn;
    private Button timerCancelBtn;
    private EditText hourEditText;
    private Spinner minSpinner;

    private Thread t;
    private boolean isCountingdown = false;
    private boolean isPausing = false;

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
        this.timerCancelBtn = (Button) findViewById(R.id.timerCancelBtn);
        timerCancelBtn.setOnClickListener(this);

        this.hourEditText = (EditText) findViewById(R.id.hourEditText);
        this.minSpinner = (Spinner) findViewById(R.id.minSpinner);
        String[] items = new String[]{"Minutes", "00", "10", "20", "30", "40", "50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        this.minSpinner.setAdapter(adapter);

        // enable back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    protected void onStop() {
        super.onStop();
        NotificationUtils.remindUserSwitchBack(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.timerStartBtn: {

                startBtnClicked();

                break;

            }
            case R.id.timerPauseBtn: {

                pauseBtnClicked();

                break;
            }

            case R.id.timerCancelBtn: {

                cancelBtnClicked();

                break;
            }
        }
    }

    private void startBtnClicked() {
        boolean hourExist = false;
        boolean minExist = false;

        // Read hours input
        try {
            hour = Integer.parseInt(String.valueOf(hourEditText.getText()));
            hourCountdown.setText(String.format("%02d", hour));
            hourExist = (hour != 0);

        } catch (Exception e) {
            hour = 0;
            hourCountdown.setText("00");
        }

        // Read minutes input
        try {
            minute = Integer.parseInt(String.valueOf(minSpinner.getSelectedItem()));
            minCountdown.setText(String.format("%02d", minute));
            minExist = (minute != 0);

        } catch (Exception e) {
            minute = 0;
            minCountdown.setText("00");
        }

        // True if either hour or minute is nonzero
        isCountingdown = hourExist || minExist;

        if (isCountingdown) {

            // Disable time selection
            this.hourEditText.setEnabled(false);
            this.hourEditText.setText("");
            this.minSpinner.setEnabled(false);
            this.minSpinner.setSelection(0);
            this.timerStartBtn.setEnabled(false);

            // Start countdown
            this.countdownThreadSetup();
        }
    }

    private void pauseBtnClicked() {
        if (isCountingdown) {
            // Pause
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_pause_timer))
                    .setMessage(getResources().getString(R.string.message_pause_timer))
                    .setNeutralButton(getResources().getString(R.string.ok_btn), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            isCountingdown = false;
                            isPausing = true;

                            timerPauseBtn.setText(getResources().getString(R.string.resume_button_timer));
                            timerCancelBtn.setEnabled(false);

                            t.interrupt();
                        }
                    }).show();
        }
        else if (isPausing){
            // Resume
            isCountingdown = true;
            isPausing = false;

            timerPauseBtn.setText(getResources().getString(R.string.pause_button_timer));
            timerCancelBtn.setEnabled(true);

            this.countdownThreadSetup();
        }
    }

    private void cancelBtnClicked() {
        if (isCountingdown) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_cancel_timer))
                    .setMessage(getResources().getString(R.string.message_cancel_timer))
                    .setPositiveButton(getResources().getString(R.string.yes_btn), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Confirmed to cancel timer, reset time and enable EditText
                            hour = 0;
                            minute = 0;
                            second = 0;

                            isCountingdown = false;

                            timerPauseBtn.setText(getResources().getString(R.string.pause_button_timer));

                            hourEditText.setEnabled(true);
                            minSpinner.setEnabled(true);
                            timerStartBtn.setEnabled(true);
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no_btn), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Nothing to do
                        }
                    }).show();
        }
    }

    private void countdownThreadSetup() {
        // Set up thread for updating countdown text

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
        // Decrement TextViews by 1 second

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


}
