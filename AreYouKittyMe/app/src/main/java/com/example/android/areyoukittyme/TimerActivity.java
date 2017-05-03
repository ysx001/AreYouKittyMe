package com.example.android.areyoukittyme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.areyoukittyme.User.User;
import com.example.android.areyoukittyme.utilities.NotificationUtils;
import com.google.gson.Gson;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private User mUser;

    private TextView hourCountdown;
    private TextView minCountdown;
    private TextView secCountdown;
    private Button timerStartBtn;
    private Button timerPauseBtn;
    private Button timerCancelBtn;
    private EditText hourEditText;
    private Spinner minSpinner;

    private Thread t;
    private boolean isCountingdown;
    private boolean isPausing;

    private int hour;
    private int minute;
    private int second;

    private int focusTime;

    private long pauseTime = 0;

    /* Called when first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();
        mUser = startingIntent.getExtras().getParcelable("User");

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
        this.minSpinner.setSelection(4);

        this.second = 0;
        this.focusTime = 0;
        this.isCountingdown = false;
        this.isPausing = false;
    }

    /* *
    * Called when menu item is selected
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when back is pressed
     */
    @Override
    public void onBackPressed() {
        Class destActivity = MainActivity.class;
        Context context = TimerActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("User", mUser);
        startActivity(intent);
    }

    /**
     * Called when the activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (isCountingdown || isPausing) {
            NotificationUtils.remindUserSwitchBack(this);
            this.pauseTime = System.currentTimeMillis();
        }
    }

    /**
     * Called when the activity is stopped
     */
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences("userPref", TimerActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }

    /**
     * Called when activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtils.setTimerActivityResumed();
        if (isCountingdown || isPausing) {
            if ((System.currentTimeMillis() - this.pauseTime) / 1000.0 > 6.0) {
                timerReset();
                timerCancelled();
            }
        }
    }

    @Override
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

    /**
     * Starts the timer. Is called when the start button is clicked.
     */
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

            // Record focus time
            this.focusTime = hour * 60 + minute;

            // Start countdown
            this.countdownThreadSetup();
        }
    }

    /**
     * Pauses the timer. Is called when the pause button clicked.
     */
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
        } else if (isPausing) {
            // Resume
            isCountingdown = true;
            isPausing = false;

            timerPauseBtn.setText(getResources().getString(R.string.pause_button_timer));
            timerCancelBtn.setEnabled(true);

            this.countdownThreadSetup();
        }
    }

    /**
     * Cancels the timer. Called when cancel button is clicked.
     */
    private void cancelBtnClicked() {
        if (isCountingdown) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_cancel_timer))
                    .setMessage(getResources().getString(R.string.message_cancel_timer))
                    .setPositiveButton(getResources().getString(R.string.yes_btn), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Confirmed to cancel timer, reset time and enable EditText
                            timerReset();
                            timerCancelled();
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

    /**
     * Resets the timer.
     */
    private void timerReset() {
        this.focusTime -= hour * 60 + minute;

        this.t.interrupt();

        hour = 0;
        minute = 0;
        second = 0;

        this.hourCountdown.setText(String.format("%02d", this.hour));
        this.minCountdown.setText(String.format("%02d", this.minute));
        this.secCountdown.setText(String.format("%02d", this.second));

        isCountingdown = false;

        timerPauseBtn.setText(getResources().getString(R.string.pause_button_timer));

        hourEditText.setEnabled(true);
        minSpinner.setEnabled(true);
        timerStartBtn.setEnabled(true);
    }

    /**
     * When timer is done, increases the health and pompts user with some text.
     */
    private void timerFinished() {
        mUser.setFocus(focusTime);
        mUser.setHealth(8 * focusTime / mUser.getFocusGoal());

        int[] moodBonus = {6, 7, 8};
        Random randomGen = new Random();
        int randomIndex = randomGen.nextInt(3);
        mUser.setMood(moodBonus[randomIndex]);

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.title_finish_timer))
                .setMessage(getResources().getString(R.string.message_finish_timer))
                .setNeutralButton(getResources().getString(R.string.dismiss_btn), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * When timer is canceled, Health and mood gets deducted and User will be prompted with text.
     */
    private void timerCancelled() {
        mUser.setFocus(focusTime);
        mUser.setHealth(8 * focusTime / mUser.getFocusGoal());
        mUser.setMood(-5);

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.title_interrupt_timer))
                .setMessage(getResources().getString(R.string.message_interrupt_timer))
                .setNeutralButton(getResources().getString(R.string.dismiss_btn), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * Creates the thread for countdown
     */
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

    /**
     * Decrement text in TextView as the countdown begins
     */
    private void textViewCountdown() {

        // Decrement TextViews by 1 second
        if (this.second > 0) {
            this.second--;
        } else {
            if (this.minute > 0) {
                this.second = 59;
                this.minute--;
            } else {
                if (this.hour > 0) {
                    this.second = 59;
                    this.minute = 59;
                    this.hour--;
                } else {
                    this.t.interrupt();
                    timerReset();
                    timerFinished();
                }
            }
        }

        this.hourCountdown.setText(String.format("%02d", this.hour));
        this.minCountdown.setText(String.format("%02d", this.minute));
        this.secCountdown.setText(String.format("%02d", this.second));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
