package com.example.smartdecorate.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DeviceDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "device_db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "device_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DEVICE_NAME = "device_name";
    private static final String COLUMN_DEVICE_IP = "device_ip";
    private static final String COLUMN_DEVICE_TYPE = "device_type";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +

            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DEVICE_NAME + " TEXT, " +
            COLUMN_DEVICE_IP + " TEXT, " +
            COLUMN_DEVICE_TYPE + " TEXT" +
            ");";

    public DeviceDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(CREATE_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertDeviceInfo(String deviceName, String deviceIp, String deviceType) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_DEVICE_NAME, deviceName);
        contentValues.put(COLUMN_DEVICE_IP, deviceIp);
        contentValues.put(COLUMN_DEVICE_TYPE, deviceType);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getDeviceInfo() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
