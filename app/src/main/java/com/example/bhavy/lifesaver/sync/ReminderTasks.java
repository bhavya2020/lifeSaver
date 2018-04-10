package com.example.bhavy.lifesaver.sync;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.bhavy.lifesaver.MainActivity;
import com.example.bhavy.lifesaver.compareActivity;
import com.example.bhavy.lifesaver.data.waterContract;
import com.example.bhavy.lifesaver.data.waterDbHelper;
import com.example.bhavy.lifesaver.utilities.NotificationUtils;
import com.example.bhavy.lifesaver.utilities.preferenceUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;



/**
 * Created by bhavy on 06-08-2017.
 */

public class ReminderTasks {
    public static final String ACTION_FULL = "full";
    public static final String ACTION_RESET = "reset";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_HALF = "half";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_NOTIFICATION="start";
     public static final int N_TIME_MINUTES=60;

    private static SQLiteDatabase mDb;
    private static final long N_TIME_SECONDS = (long) (TimeUnit.MINUTES.toMillis(N_TIME_MINUTES));
    public static void executeTask(Context context, String action) throws PendingIntent.CanceledException, ParseException {

        if (action.equals(ACTION_HALF)) {
            incrementHalf(context);
        }
        else if(action.equals(ACTION_FULL))
        {
            incrementFull(context);
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        }
        else if(ACTION_NOTIFICATION.equals(action))
        {
           notification(context);
        }
        else if(ACTION_RESET.equals(action))
        {

            int count;
            int id=preferenceUtilities.getId(context);
            id++;
           preferenceUtilities.setId(context,id);
            count=0;
            preferenceUtilities.setCount(context,count);

        }
        else if(ACTION_DELETE.equals(action))
        {
            int id=preferenceUtilities.getId(context);
            id--;
            preferenceUtilities.setId(context,id);
        }
    }

    private static void notification(Context context) throws PendingIntent.CanceledException {
    //   long StoredTime =preferenceUtilities.getTime(context);
     //   long CurrentTime=preferenceUtilities.getTimeFromSystem();
//if(CurrentTime-StoredTime>N_TIME_SECONDS)
        NotificationUtils.remindUser(context);
    }

    private static void incrementHalf(Context context) {
        preferenceUtilities.halfIncrement(context);

    }
    private static void incrementFull(Context context) {
        preferenceUtilities.fullIncrement(context);

    }

}
