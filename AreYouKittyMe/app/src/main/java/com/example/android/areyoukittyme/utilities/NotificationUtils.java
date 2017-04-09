package com.example.android.areyoukittyme.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.areyoukittyme.MainActivity;
import com.example.android.areyoukittyme.R;

/**
 * Created by sarah on 4/9/17.
 * Utility class for creating hydration notification
 */

public class NotificationUtils {
    private static final int REMINDER_NOTIFICATION_ID = 1017;
    private static final int REMINDER_PENDING_INTENT_ID = 2013;


    /**
     * Create a method called remindUserBecauseCharging which takes a Context.
     * This method will create a notification for charging. https://developer.android
     * .com/training/notify-user/build-notification.html
     *
     * @param context
     */
    public static void remindUserSwitchBack(Context context) {

        // - sets the notification defaults to vibrate
        // - uses the content intent returned by the contentIntent helper method for the contentIntent
        // - automatically cancels the notification when the notification is clicked
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(context.getString(R.string.reminder_notification_title))
                .setContentText(context.getString(R.string.reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        // If the build version is greater than JELLY_BEAN, set the notification's priority
        // to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        // Get a NotificationManager, using context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Trigger the notification by calling notify on the NotificationManager.
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Create a helper method called contentIntent with a single parameter for a Context. It
     * should return a PendingIntent.
     *
     * @param context
     * @return PendingIntent will trigger when the notification is pressed
     * This pending intent should open up the MainActivity.
     */
    private static PendingIntent contentIntent(Context context) {
        // Create an intent that opens up the MainActivity
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        // Has the flag FLAG_UPDATE_CURRENT, so that if the intent is created again, keep the
        // intent but update the data
        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

    }

    // Create a helper method called largeIcon which takes in a Context as a parameter and
    // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
    private static Bitmap largeIcon(Context context) {
        // Get a Resources object from the context.
        Resources res = context.getResources();

        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_notifications_black_24dp);
        return largeIcon;
    }
}
