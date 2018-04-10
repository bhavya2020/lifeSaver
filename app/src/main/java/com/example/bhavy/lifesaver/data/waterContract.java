package com.example.bhavy.lifesaver.data;

import android.provider.BaseColumns;

/**
 * Created by bhavy on 11-08-2017.
 */

public class waterContract {
    public static final class WaterDay implements BaseColumns {
        public static final String TABLE_NAME = "water";
        public static final String ID = "id";
        public static final String COUNT= "count";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
