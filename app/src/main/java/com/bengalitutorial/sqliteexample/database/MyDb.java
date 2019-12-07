package com.bengalitutorial.sqliteexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDb extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATA_BASE_NAME = "mydb.db";

    public static final String TABLE_NAME = "friends";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String _DATE = "date";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FIRST_NAME + " TEXT, "
            + LAST_NAME + " TEXT, "
            + _DATE + " DATE)";

    private static final String DELETE_CREATE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String TAG = "MyDb";


    public MyDb(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.v(TAG, "onCreate: Data Base Create.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_CREATE);
        Log.v(TAG, "onUpgrade: Data Base Upgrade.");
        onCreate(db);
    }

    public void insertData(SQLiteDatabase db,String fname,String lname) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String date = simpleDateFormat.format(new Date());
        ContentValues values = new ContentValues(3);
        values.put(FIRST_NAME, fname);
        values.put(LAST_NAME, lname);
        values.put(_DATE, simpleDateFormat.format(new Date()));

        long newData =db.insert(TABLE_NAME, null, values);
        Log.v(TAG, ""+newData);
    }
}
