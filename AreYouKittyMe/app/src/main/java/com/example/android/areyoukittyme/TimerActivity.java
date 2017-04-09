package com.example.android.areyoukittyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.areyoukittyme.utilities.NotificationUtils;

public class TimerActivity extends AppCompatActivity {

    private Button testNotificationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        testNotificationButton = (Button) findViewById(R.id.test_notification_btn);

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
