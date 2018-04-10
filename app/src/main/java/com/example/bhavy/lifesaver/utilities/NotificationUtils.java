package com.example.bhavy.lifesaver.utilities;

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

import com.example.bhavy.lifesaver.MainActivity;
import com.example.bhavy.lifesaver.R;
import com.example.bhavy.lifesaver.sync.ReminderTasks;
import com.example.bhavy.lifesaver.sync.WaterReminderIntentService;

import java.util.zip.CheckedOutputStream;

/**
 * Created by bhavy on 09-08-2017.
 */

public class NotificationUtils {
    private static final int WATER_REMINDER_NOTIFICATION_ID = 2020;
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 2016;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 23;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
    public static void remindUser(Context context) throws PendingIntent.CanceledException {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.launcher_icon)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .addAction(drinkWaterAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
    private static NotificationCompat.Action drinkWaterAction(Context context) throws PendingIntent.CanceledException {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent p= PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action drinkWaterAction = new NotificationCompat.Action(0,
                "Ya,sure", p);
        return drinkWaterAction;

    }
    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, WaterReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(0,
                "No, thanks.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res,R.drawable.full_glass);
        return largeIcon;
    }


}
