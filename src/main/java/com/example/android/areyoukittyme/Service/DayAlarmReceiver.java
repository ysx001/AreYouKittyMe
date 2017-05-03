package com.example.android.areyoukittyme.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DayAlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, newDayService.class);
        context.startService(i);
    }
}
