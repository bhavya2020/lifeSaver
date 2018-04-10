package com.example.bhavy.lifesaver.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.bhavy.lifesaver.sync.ReminderTasks;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bhavy on 06-08-2017.
 */

public final class preferenceUtilities {
    public static final String COUNT = "count";
    public static final String TIME = "time";
    public static final int COUNT_D =0;
    public static final long TIME_D =0;
    public static final String ID = "Id";
    public static final int ID_D =1;

       synchronized public static void setCount(Context context, int count) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor=pref.edit();
            editor.putInt("count",count);
            editor.apply();
        }
    synchronized public static void setTim(Context context, long time) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putLong(TIME,time);
        editor.apply();

    }
        synchronized public static long getTime(Context context)
        {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            long seconds  = pref.getLong(TIME, TIME_D);
            return seconds;
        }
       public static int getCount(Context context) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            int count  = pref.getInt(COUNT, COUNT_D);
            return count;
        }
       synchronized public static void halfIncrement(Context context)
        {
            int count=preferenceUtilities.getCount(context);
            count=count+1;
            setCount(context,count);
            long time=preferenceUtilities.getTimeFromSystem();
            setTim(context,time);

        }
       synchronized public static void fullIncrement(Context context)
        {
            int count=preferenceUtilities.getCount(context);
            count=count+2;
            setCount(context,count);
            long time=preferenceUtilities.getTimeFromSystem();
            setTim(context,time);

        }
        synchronized public static long getTimeFromSystem()
        {
            Calendar c = Calendar.getInstance();
            Long seconds = c.getTimeInMillis();
            return seconds;
         }
         synchronized public static int getId(Context context)
         {
             SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
             int id  = pref.getInt(ID, ID_D);
             return id;
         }
    synchronized public static void setId(Context context, int id) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt(ID,id);
        editor.apply();
    }

    }


