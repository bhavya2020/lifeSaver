package com.example.bhavy.lifesaver.sync;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.bhavy.lifesaver.MainActivity;

import java.text.ParseException;

/**
 * Created by bhavy on 06-08-2017.
 */

public class WaterReminderIntentService extends IntentService {

    public WaterReminderIntentService() {
        super("WaterReminderIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        String action = intent.getAction();
        try {
            ReminderTasks.executeTask(this, action);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
