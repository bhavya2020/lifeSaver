package com.example.bhavy.lifesaver.sync;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;

/**
 * Created by bhavy on 11-08-2017.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {

        try {
            ReminderTasks.executeTask(context,ReminderTasks.ACTION_RESET);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
