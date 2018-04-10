package com.example.bhavy.lifesaver;
import com.example.bhavy.lifesaver.data.waterContract;
import com.example.bhavy.lifesaver.data.waterDbHelper;
import com.example.bhavy.lifesaver.sync.AlarmReceiver;
import com.example.bhavy.lifesaver.sync.ReminderUtilities;
import com.example.bhavy.lifesaver.sync.resetUtilities;
import com.example.bhavy.lifesaver.utilities.preferenceUtilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhavy.lifesaver.sync.ReminderTasks;
import com.example.bhavy.lifesaver.sync.WaterReminderIntentService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

 ImageButton half,full;
    TextView mCount;
    private  SQLiteDatabase mDb;


  //  public static Toast t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  t=Toast.makeText(MainActivity.this,"hi",Toast.LENGTH_LONG);
//scheduleAlarm();
/*
        Timer mytimer = new Timer("My Timer");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 5);

        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ReminderTasks.executeTask(MainActivity.this,ReminderTasks.ACTION_RESET);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, c.getTime(), 24 * 60 * 60 * 1000);*/
        waterDbHelper dbHelper = new waterDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor =getAllGuests();
        mCount= (TextView) findViewById(R.id.value);
        half= (ImageButton) findViewById(R.id.half_glass);
         half.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incrementWaterCountIntent = new Intent(MainActivity.this, WaterReminderIntentService.class);
                incrementWaterCountIntent.setAction(ReminderTasks.ACTION_HALF);
                startService(incrementWaterCountIntent);

                int id = preferenceUtilities.getId(MainActivity.this);
                int count = preferenceUtilities.getCount(MainActivity.this);
                count++;



                Cursor cursor = mDb.query(waterContract.WaterDay.TABLE_NAME,new String[] {waterContract.WaterDay.ID},null,null,null,null,null,null);
                int id2=1,i=0;
                if(cursor != null)
                {
                    if (cursor.moveToFirst()) {
                        int x=cursor.getColumnIndex(waterContract.WaterDay.ID);
                        id2=cursor.getInt(x);
                    }
                    if(cursor.moveToLast()){
                        int x=cursor.getColumnIndex(waterContract.WaterDay.ID);
                        i=cursor.getInt(x);
                    }
                }
                cursor.close();

                         if ( i==id-1) {
                             addCount(count, id);
                             if(i+1>7)
                             {
                                deleteCount(id2);
                   // Intent Intent = new Intent(MainActivity.this, WaterReminderIntentService.class);
                            //   incrementWaterCountIntent.setAction(ReminderTasks.ACTION_DELETE);
                               // startService(incrementWaterCountIntent);
                             }
                             else {}

                          } else {

                                   updateCount(count,id);
                                }

            }
        });
       full= (ImageButton) findViewById(R.id.full_glass);
        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incrementWaterCountIntent = new Intent(MainActivity.this, WaterReminderIntentService.class);
                incrementWaterCountIntent.setAction(ReminderTasks.ACTION_FULL);
                startService(incrementWaterCountIntent);

                int id=preferenceUtilities.getId(MainActivity.this);
                int count=preferenceUtilities.getCount(MainActivity.this);
                count=count+2;
                Cursor cursor = mDb.query(waterContract.WaterDay.TABLE_NAME,new String[] {waterContract.WaterDay.ID},null,null,null,null,null,null);
                int id2=1,i=0;
                if(cursor != null)
                {
                    if (cursor.moveToFirst()) {
                        int x=cursor.getColumnIndex(waterContract.WaterDay.ID);
                        id2=cursor.getInt(x);
                    }
                    if(cursor.moveToLast()){
                        int x=cursor.getColumnIndex(waterContract.WaterDay.ID);
                        i=cursor.getInt(x);
                    }
                }
                cursor.close();

                if ( i==id-1) {
                    addCount(count, id);
                    if(i+1>7)
                     {
                    deleteCount(id2);
                  //   Intent Intent = new Intent(MainActivity.this, WaterReminderIntentService.class);
                //   incrementWaterCountIntent.setAction(ReminderTasks.ACTION_DELETE);
                 //   startService(incrementWaterCountIntent);
                      }
                } else {
                    updateCount(count,id);
                }

            }
        });


         updateWaterCount();
        setupSharedPreferences();
        ReminderUtilities.scheduleReminder(this);
        resetUtilities.scheduleReminder(this);

    }
    public long updateCount(int count,int id)
    {
        ContentValues cv = new ContentValues();
        cv.put(waterContract.WaterDay.COUNT, count);
        cv.put(waterContract.WaterDay.ID,id);
        Date date=new Date();
        String  s= String.valueOf(date);
        cv.put(waterContract.WaterDay.COLUMN_TIMESTAMP,s.substring(0,11));
        long x=mDb.update(waterContract.WaterDay.TABLE_NAME,cv,waterContract.WaterDay.ID + "=" + id ,null);
        return x;
    }
    public long addCount(int count,int id)
    {
        ContentValues cv = new ContentValues();
        cv.put(waterContract.WaterDay.COUNT, count);
        cv.put(waterContract.WaterDay.ID,id);
      //  DateFormat df=new SimpleDateFormat("mm-dd hh:mm:ss");
        Date date=new Date();
        String  s= String.valueOf(date);
        cv.put(waterContract.WaterDay.COLUMN_TIMESTAMP,s.substring(0,11));
        return (mDb.insert(waterContract.WaterDay.TABLE_NAME, null, cv));
    }
    public long deleteCount(int id)
    {
        long x=mDb.delete(waterContract.WaterDay.TABLE_NAME,waterContract.WaterDay.ID + "=" + id ,null);
        return x;
    }

    /*  public void delete(int id) {
       // int b=  mDb.delete(waterContract.WaterDay.TABLE_NAME, waterContract.WaterDay._ID +"=?", new String[]{Integer.toString(id)});
         mDb.execSQL("delete from "+waterContract.WaterDay.TABLE_NAME+" where "+ waterContract.WaterDay._ID +"='"+id+"'");
         // boolean b= mDb.delete(waterContract.WaterDay.TABLE_NAME, waterContract.WaterDay._ID + "=" + id, null) > 0;

        // return b;
     }

    @Override
     protected void onStart() {

         Intent intentAlarm = new Intent(this, AlarmReceiver.class);

         // Get the Alarm Service.
         Calendar calendar = Calendar.getInstance();

         calendar.set(Calendar.HOUR_OF_DAY, 19);
         calendar.set(Calendar.MINUTE, 8);
         AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

         // Set the alarm for a particular time.

         alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

 super.onStart();
     }*/
  public  Cursor getAllGuests() {
      return mDb.query(
              waterContract.WaterDay.TABLE_NAME,
              null,
              null,
              null,
              null,
              null,
              waterContract.WaterDay.COLUMN_TIMESTAMP
      );
  }

    private void setupSharedPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);
    }
    private void updateWaterCount() {
        int Count = preferenceUtilities.getCount(this);
        mCount.setText(String.valueOf((float)Count/2));
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(preferenceUtilities.COUNT))
        {
            updateWaterCount();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.unregisterOnSharedPreferenceChangeListener(this);
       /* AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        manager.cancel(PendingIntent.getBroadcast(this,1,intentAlarm,PendingIntent.FLAG_UPDATE_CURRENT));
        super.onStart();*/
      
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_compare) {
            startActivity(new Intent(this, compareActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void scheduleAlarm()
    {
        // The time at which the alarm will be scheduled. Here the alarm is scheduled for 1 day from the current time.
        // We fetch the current time in milliseconds and add 1 day's time
        // i.e. 24*60*60*1000 = 86,400,000 milliseconds in a day.

      //  Long time = new GregorianCalendar().getTimeInMillis()+2*1000;

        // Create an Intent and set the class that will execute when the Alarm triggers. Here we have
        // specified AlarmReceiver in the Intent. The onReceive() method of this class will execute when the broadcast from your alarm is received.
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // Get the Alarm Service.
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 8);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm for a particular time.

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));


    }


}
