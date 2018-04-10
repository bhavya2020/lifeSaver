package com.example.bhavy.lifesaver.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bhavy on 11-08-2017.
 */

public class waterDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "water.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public waterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + waterContract.WaterDay.TABLE_NAME + " (" +
                waterContract.WaterDay.ID + " INTEGER ," +
               waterContract.WaterDay.COUNT + " INTEGER , " +
                waterContract.WaterDay.COLUMN_TIMESTAMP + " STRING " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +waterContract.WaterDay.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}