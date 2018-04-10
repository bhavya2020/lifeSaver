package com.example.bhavy.lifesaver.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by bhavy on 11-08-2017.
 */

public class resetUtilities {
    private static final int REMINDER_INTERVAL_HOURS =24 ;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.HOURS.toSeconds(REMINDER_INTERVAL_HOURS));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_JOB_TAG = "reset_tag";
    private static boolean sInitialized;

    synchronized public static void scheduleReminder(@NonNull final Context context) {
        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job ReminderJob = dispatcher.newJobBuilder()
                .setService(resetJob.class)

                .setTag(REMINDER_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                      REMINDER_INTERVAL_SECONDS,REMINDER_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(ReminderJob);
        sInitialized = true;
    }
}
